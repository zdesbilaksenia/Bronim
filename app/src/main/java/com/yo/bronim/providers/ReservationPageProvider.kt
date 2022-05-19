package com.yo.bronim.providers

import android.util.Log
import com.yo.bronim.models.PostReservation
import com.yo.bronim.models.Reservation
import com.yo.bronim.repository.ReservationPageRepository
import java.io.IOException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ReservationPageProvider {
    private val scope = CoroutineScope(Dispatchers.IO)

    private val reservationPageRepository = ReservationPageRepository()

    private suspend fun invokeCallback(
        callback: (result: Array<Reservation>?, error: Throwable?) -> Unit,
        result: Array<Reservation>?,
        error: Throwable?
    ) {
        withContext(Dispatchers.Main) {
            callback(result, error)
        }
    }

    fun getAvailableTablesAndTime(
        callback: (
            result: Array<Reservation>?,
            error: Throwable?
        ) -> Unit,
        restId: Int,
        date: String,
        numOfGuests: Int
    ) {
        scope.launch {
            try {
                val result =
                    reservationPageRepository.getAvailableTablesAndTime(restId, date, numOfGuests)
                invokeCallback(callback, result, null)
            } catch (error: IOException) {
                invokeCallback(callback, null, error)
            }
        }
    }

    fun sendReservationInfo(postReservation: PostReservation, restId: Int, chosenTable: Int) {
        scope.launch {
            reservationPageRepository.sendReservationInfo(postReservation, restId, chosenTable)
        }
    }
}
