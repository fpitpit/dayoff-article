package fr.pitdev.dayoff.data.utils

import com.squareup.moshi.JsonDataException
import fr.pitdev.dayoff.common.base.DayOffException
import fr.pitdev.dayoff.common.base.utils.UNKNOWN_NETWORK_EXCEPTION
import fr.pitdev.dayoff.common.utils.network.NetworkStatus
import retrofit2.HttpException
import retrofit2.Response
import java.net.ConnectException
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
                NetworkStatus.Exception(DayOffException.ConnectException(it))

            is DayOffException.SocketTimeOutException -> NetworkStatus.Exception(
                DayOffException.SocketTimeOutException(
                    it
                )
            )
            is JsonDataException -> NetworkStatus.Exception(DayOffException.JsonDataException(it))
            is UnknownHostException -> NetworkStatus.Exception(
                DayOffException.UnknownHostException(
                    it
                )
            )
            is HttpException -> NetworkStatus.Exception(DayOffException.HttpException(it))
            else -> NetworkStatus.Exception(DayOffException.UnknownNetworkException(it))
        }
    }.getOrElse { return NetworkStatus.Error(UNKNOWN_NETWORK_EXCEPTION) }
}
