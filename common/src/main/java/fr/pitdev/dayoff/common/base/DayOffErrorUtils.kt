package fr.pitdev.dayoff.common.base

import fr.pitdev.dayoff.common.base.utils.CONNECT_EXCEPTION
import fr.pitdev.dayoff.common.base.utils.SOCKET_TIME_OUT_EXCEPTION
import fr.pitdev.dayoff.common.base.utils.UNKNOWN_NETWORK_EXCEPTION

sealed class DayOffException(
    override val cause: Throwable,
    override val message: String? = cause.message
) : Exception(message ?: cause.message, cause) {
    class SocketTimeOutException(cause: Throwable, message: String? = SOCKET_TIME_OUT_EXCEPTION) :
        DayOffException(cause, message = message)

    class JsonDataException(cause: Throwable, message: String? = cause.message) :
        DayOffException(cause, message = message)

    class UnknownHostException(cause: Throwable, message: String? = UNKNOWN_NETWORK_EXCEPTION) :
        DayOffException(cause, message = message)

    class HttpException(cause: Throwable, message: String? = cause.message) :
        DayOffException(cause, message = message)

    class UnknownNetworkException(cause: Throwable, message: String? = UNKNOWN_NETWORK_EXCEPTION) :
        DayOffException(cause, message)

    class ConnectException(cause: Throwable, message: String? = CONNECT_EXCEPTION) :
        DayOffException(cause, message)

}