package com.yo.bronim.providers

import com.yo.bronim.models.Reservation
import com.yo.bronim.models.ReservationsListCallback
import com.yo.bronim.repository.ReservationsListPageRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ReservationsListPageProvider {
    private val scope = CoroutineScope(Dispatchers.IO)
    private val reservationsListPageRepository = ReservationsListPageRepository()

    private suspend fun invokeCallback(
        callback: (result: Array<Reservation>?, error: Throwable?) -> Unit,
        result: Array<Reservation>?,
        error: Throwable?
    ) {
        withContext(Dispatchers.Main) {
            callback(result, error)
        }
    }

    fun getReservationsList(callback: ReservationsListCallback) {
        scope.launch {
            try {
                val result = reservationsListPageRepository.getReservationsList()
                invokeCallback(callback, result, null)
            } catch (error: Throwable) {
                invokeCallback(callback, null, error)
            }
        }
    }
}