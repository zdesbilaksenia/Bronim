package com.yo.bronim.viewmodels

import androidx.lifecycle.MutableLiveData
import com.yo.bronim.managers.ReservationPageManager
import com.yo.bronim.models.PostReservation
import com.yo.bronim.states.ReservationPageState

class ReservationPageViewModel(callback: (Int) -> Unit) {
    private val reservationPageManager = ReservationPageManager(callback)
    val reservationsState = MutableLiveData<ReservationPageState>()

    fun getAvailableTablesAndTime(
        restId: Int,
        date: String,
        numOfGuests: Int,
    ) {
        reservationsState.postValue(ReservationPageState.Pending())

        reservationPageManager.getAvailableTablesAndTime(
            restId,
            date,
            numOfGuests
        ) { result, error ->
            when {
                error != null -> {
                    reservationsState.postValue(ReservationPageState.Error(error))
                }
                else -> reservationsState.postValue(ReservationPageState.Success(result))
            }
        }
    }

    fun sendReservationInfo(postReservation: PostReservation, restId: Int, chosenTable: Int) {
        reservationPageManager.sendReservationInfo(postReservation, restId, chosenTable)
    }
}
