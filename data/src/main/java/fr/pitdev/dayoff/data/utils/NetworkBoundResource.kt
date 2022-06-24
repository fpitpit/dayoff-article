package fr.pitdev.dayoff.data.utils

import fr.pitdev.dayoff.common.utils.network.NetworkStatus
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.*

inline fun <ResultType, RequestType> networkBoundResource(
    crossinline query: () -> Flow<ResultType>,
    crossinline fetch: suspend () -> RequestType,
    crossinline saveFetchResult: suspend (RequestType) -> Unit,
    crossinline clearData: suspend () -> Unit,
    crossinline onFetchFailed: (Throwable) -> Unit = { },
    crossinline shouldFetch: (ResultType) -> Boolean = { true },
    crossinline shouldClear: (RequestType, ResultType) -> Boolean = { _: RequestType, _: ResultType -> false },
    coroutineDispatcher: CoroutineDispatcher
) = flow<NetworkStatus<ResultType>> {
    val dbData = query().first()
    if (shouldFetch(dbData)) {
        emit(NetworkStatus.Loading)
        val fetchData = fetch()
        if (shouldClear(fetchData, dbData)) {
            clearData()
        }
        when (fetchData) {
            is NetworkStatus.Success<*> -> saveFetchResult(fetchData)
            is NetworkStatus.Error -> {
                fetchData.throwable?.let(onFetchFailed)
                emit(fetchData)
            }
        }
        val updatedData = query().first()
        emit(NetworkStatus.Success(updatedData))
    } else {
        emit(NetworkStatus.Success(dbData))
    }
}.catch {
    onFetchFailed(it)
    emit(NetworkStatus.Error(throwable = it, errorMessage = it.message))
}.flowOn(coroutineDispatcher)

