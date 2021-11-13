package fr.pitdev.dayoff.data.repository

import android.util.Log
import fr.pitdev.dayoff.common.utils.network.NetworkStatus
import fr.pitdev.dayoff.data.coroutines.DispatchProvider
import fr.pitdev.dayoff.data.dtos.DayOffDto
import fr.pitdev.dayoff.data.dtos.toDomain
import fr.pitdev.dayoff.data.dtos.toDto
import fr.pitdev.dayoff.data.local.datasource.LocalDataSource
import fr.pitdev.dayoff.data.remote.datasource.DayOffRemoteDataSource
import fr.pitdev.dayoff.data.utils.networkBoundResource
import fr.pitdev.dayoff.domain.models.DayOff
import fr.pitdev.dayoff.domain.models.Zone
import fr.pitdev.dayoff.domain.repository.DayOffRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class DayOffRepositoryImpl(
    private val dispatchProvider: DispatchProvider,
    private val remoteDataSource: DayOffRemoteDataSource,
    private val localDataSource: LocalDataSource
) : DayOffRepository {
    override suspend fun getDayOffs(zone: Zone, year: Int): Flow<NetworkStatus<List<DayOff>>> {
        return networkBoundResource(
            query = { fetchLocalDayOff(zone, year) },
            fetch = { fetchRemoteData(zone, year) },
            onFetchFailed = { throw it },
            saveFetchResult = { response ->
                saveData(response, zone)
            },
            clearData = {},
            coroutineDispatcher = dispatchProvider.io()
        )
    }

    private suspend fun fetchRemoteData(
        zone: Zone,
        year: Int
    ) = withContext(dispatchProvider.io()) {
        val result = remoteDataSource.getAll(zone.toDto(), year)
        return@withContext result
    }

    private suspend fun saveData(
        response: NetworkStatus<DayOffDto>,
        zone: Zone
    ) {
        if (response is NetworkStatus.Success) {
            Log.d(TAG, "saveData: ")
            val data = response.data
            localDataSource.saveDayOffs(data.toDomain(zoneDto = zone.toDto()))
        }
    }

    private fun fetchLocalDayOff(zone: Zone, year: Int): Flow<List<DayOff>> =
        localDataSource.getAllDayOff(zone, year)

    companion object {
        val TAG = DayOffRepositoryImpl::class.java.simpleName
    }

}