package fr.pitdev.dayoff.data.utils

import kotlinx.coroutines.flow.*

inline fun <ResultType, RequestType> networkBoundResource(
    crossinline query: () -> Flow<ResultType>,
    crossinline fetch: suspend () -> RequestType,
    crossinline saveFetchResult: suspend (RequestType) -> Unit,
    crossinline clearData: suspend () -> Unit,
    crossinline onFetchFailed: (Throwable) -> Unit = { Unit },
    crossinline shouldFetch: (ResultType) -> Boolean = { true },
    crossinline shouldClear: (RequestType, ResultType) -> Boolean = { requestType: RequestType, resultType: ResultType -> false }

) = flow<NetworkStatus<ResultType>> {
    emit(NetworkStatus.Loading(null))
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
            query().map { NetworkStatus.Error(error.localizedMessage, it) }
        }.getOrDefault(flow { NetworkStatus.Error("toto", null) })
    } else {
        query().map { NetworkStatus.Success(it) }
    }
    emitAll(flow)
}

sealed class NetworkStatus<T>(val data: T? = null, val errorMessage: String? = null) {
    class Success<T>(data: T?) : NetworkStatus<T>(data)
    class Error<T>(errorMessage: String?, data: T? = null) : NetworkStatus<T>(data, errorMessage)
    class Loading<T>(data: T? = null) : NetworkStatus<T>(data)
}
