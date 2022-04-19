package com.yo.bronim.managers

import com.yo.bronim.models.ReservationsListCallback
import com.yo.bronim.providers.ReservationsListPageProvider

class ReservationsListPageManager {
    private val reservationsListPageProvider = ReservationsListPageProvider()

    fun getReservationsList(callback: ReservationsListCallback) {
        reservationsListPageProvider.getReservationsList { result, error ->  
            callback(result, error)
        }
    }
}
