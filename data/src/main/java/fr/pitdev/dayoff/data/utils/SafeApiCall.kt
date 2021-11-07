package fr.pitdev.dayoff.data.utils

import fr.pitdev.dayoff.common.base.utils.CONNECT_EXCEPTION
import fr.pitdev.dayoff.common.base.utils.UNKNOWN_HOST_EXCEPTION
import fr.pitdev.dayoff.common.base.utils.UNKNOWN_NETWORK_EXCEPTION
import retrofit2.HttpException
import retrofit2.Response
import java.net.ConnectException
import java.net.UnknownHostException

suspend fun <T : Any> safeApiCall(call: suspend () -> Response<T>): NetworkStatus<T> {
    runCatching {
        val response = call.invoke()
        if (response.isSuccessful) {
            response.body()?.let {
                return NetworkStatus.Success(response.body())
            }
        }
        return NetworkStatus.Error(response.message())

    }.onFailure {
        return when (it) {
            is ConnectException -> NetworkStatus.Error(CONNECT_EXCEPTION)
            is UnknownHostException -> NetworkStatus.Error(UNKNOWN_HOST_EXCEPTION)
            is HttpException -> NetworkStatus.Error(UNKNOWN_NETWORK_EXCEPTION)
            else -> NetworkStatus.Error(UNKNOWN_NETWORK_EXCEPTION)
        }
    }.getOrElse { return NetworkStatus.Error(UNKNOWN_NETWORK_EXCEPTION) }
}
