package fr.pitdev.dayoff.common.utils.network

sealed class NetworkStatus<out T> {
    data class Success<out T>(val data: T) : NetworkStatus<T>()
    data class Error(val errorMessage: String? = null, val throwable: Throwable? = null) :
        NetworkStatus<Nothing>()
    object Loading : NetworkStatus<Nothing>()
}