package fr.pitdev.dayoff.common.utils.network

import fr.pitdev.dayoff.common.base.DayOffException

sealed class NetworkStatus<out T> {
    data class Success<out T>(val data: T) : NetworkStatus<T>()
    data class Error(val errorMessage: String? = null, val throwable: Throwable? = null) :
        NetworkStatus<Nothing>()
    data class Exception(val dayOffException: DayOffException): NetworkStatus<Nothing>()
    object Loading : NetworkStatus<Nothing>()
}