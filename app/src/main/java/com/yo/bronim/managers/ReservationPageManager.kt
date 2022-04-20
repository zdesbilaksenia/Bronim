package com.yo.bronim.managers

import com.yo.bronim.models.PostReservation
import com.yo.bronim.models.Reservation
import com.yo.bronim.providers.ReservationPageProvider

class ReservationPageManager {
    private val reservationPageProvider = ReservationPageProvider()

    fun getAvailableTablesAndTime(
        restId: Int,
        date: String,
        numOfGuests: Int,
        callback: (
            result: Array<Reservation>?,
            error: Throwable?
        ) -> Unit
    ) {
        reservationPageProvider.getAvailableTablesAndTime(
            { result, error ->
                callback(result, error)
            },
            restId,
            date,
            numOfGuests
        )
    }

    fun sendReservationInfo(postReservation: PostReservation, restId: Int, chosenTable: Int) {
        reservationPageProvider.sendReservationInfo(postReservation, restId, chosenTable)
    }
}