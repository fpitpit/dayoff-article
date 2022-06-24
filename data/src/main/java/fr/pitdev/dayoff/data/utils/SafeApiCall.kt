package fr.pitdev.dayoff.data.utils

import com.squareup.moshi.JsonDataException
import fr.pitdev.dayoff.common.base.utils.*
import fr.pitdev.dayoff.common.utils.network.NetworkStatus
import retrofit2.HttpException
import retrofit2.Response
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

suspend fun <T : Any> safeApiCall(call: suspend () -> Response<T>): NetworkStatus<T> {
    runCatching {
        val response = call()
        if (response.isSuccessful) {
            response.body()?.let {
                return NetworkStatus.Success(it)
            }
        }
        return NetworkStatus.Error(response.message())

    }.onFailure {
        return when (it) {
            is ConnectException ->
                NetworkStatus.Error(CONNECT_EXCEPTION, it)
            is SocketTimeoutException -> NetworkStatus.Error(SOCKET_TIME_OUT_EXCEPTION, it)
            is JsonDataException -> NetworkStatus.Error(JSON_EXCEPTION, it)
            is UnknownHostException -> NetworkStatus.Error(UNKNOWN_HOST_EXCEPTION, it)
            is HttpException -> NetworkStatus.Error(HTTP_EXCEPTION, it)
            else -> NetworkStatus.Error(UNKNOWN_NETWORK_EXCEPTION, it)
        }
    }.getOrElse { return NetworkStatus.Error(UNKNOWN_NETWORK_EXCEPTION) }
}
