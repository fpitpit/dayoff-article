package fr.pitdev.dayoff.data.utils

import fr.pitdev.dayoff.common.utils.network.NetworkStatus
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.*

inline fun <ResultType, RequestType> networkBoundResource(
    crossinline query: () -> Flow<ResultType>,
    crossinline fetch: suspend () -> RequestType,
    crossinline saveFetchResult: suspend (RequestType) -> Unit,
    crossinline clearData: suspend () -> Unit,
    crossinline onFetchFailed: (Throwable) -> Unit = { Unit },
    crossinline shouldFetch: (ResultType) -> Boolean = { true },
    crossinline shouldClear: (RequestType, ResultType) -> Boolean = { _: RequestType, _: ResultType -> false },
    coroutineDispatcher: CoroutineDispatcher
) = flow<NetworkStatus<ResultType>> {
    val dbData = query().first()
    if (shouldFetch(dbData)) {
        val fetchData = fetch()
        if (shouldClear(fetchData, dbData)) {
            clearData()
        }
        if (fetchData is NetworkStatus.Error) {
            fetchData.throwable?.let { throw it }
        } else {
            saveFetchResult(fetchData)
        }
        val updatedData = query().first()
        emit(NetworkStatus.Success(updatedData))
    } else {
        emit(NetworkStatus.Success(dbData))
    }
}.onStart {
    emit(NetworkStatus.Loading)
}.catch {
    onFetchFailed(it)
    emit(NetworkStatus.Error(throwable = it, errorMessage = it.message))
}.flowOn(coroutineDispatcher)

