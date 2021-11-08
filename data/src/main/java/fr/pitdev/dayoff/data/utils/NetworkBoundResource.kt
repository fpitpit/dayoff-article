package fr.pitdev.dayoff.data.utils

import fr.pitdev.dayoff.common.base.utils.UNKNOWN_NETWORK_EXCEPTION
import kotlinx.coroutines.flow.*

inline fun <ResultType, RequestType> networkBoundResource(
    crossinline query: () -> Flow<ResultType>,
    crossinline fetch: suspend () -> RequestType,
    crossinline saveFetchResult: suspend (RequestType) -> Unit,
    crossinline clearData: suspend () -> Unit,
    crossinline onFetchFailed: (Throwable) -> Unit = { Unit },
    crossinline shouldFetch: (ResultType) -> Boolean = { true },
    crossinline shouldClear: (RequestType, ResultType) -> Boolean = { _: RequestType, _: ResultType -> false }

) = flow<NetworkStatus<ResultType>> {
    emit(NetworkStatus.Loading)
    val dbData = query().first()
    val flow = if (shouldFetch(dbData)) {
        kotlin.runCatching {
            val fetchData = fetch()
            if (shouldClear(fetchData, dbData)) {
                clearData()
            }
            saveFetchResult(fetchData)
            query().map { NetworkStatus.Success(it) }
        }.onFailure { error ->
            onFetchFailed(error)
            query().map { NetworkStatus.Error(error.localizedMessage, error) }
        }.getOrDefault(flow { NetworkStatus.Error(UNKNOWN_NETWORK_EXCEPTION) })
    } else {
        query().map { NetworkStatus.Success(it) }
    }
    emitAll(flow)
}

sealed class NetworkStatus<out T> {
    data class Success<out T>(val data: T) : NetworkStatus<T>()
    data class Error(val errorMessage: String? = null, val throwable: Throwable? = null) :
        NetworkStatus<Nothing>()

    object Loading : NetworkStatus<Nothing>()
}
