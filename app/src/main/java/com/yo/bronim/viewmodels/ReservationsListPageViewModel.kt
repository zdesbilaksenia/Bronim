package com.yo.bronim.viewmodels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.yo.bronim.managers.ReservationsListPageManager
import com.yo.bronim.states.ReservationsListState

class ReservationsListPageViewModel {
    private val reservationListPageManager = ReservationsListPageManager()
    val reservationsListState = MutableLiveData<ReservationsListState>()

    fun getReservationsList() {
        reservationsListState.postValue(ReservationsListState.Pending)

        reservationListPageManager.getReservationsList { result, error ->
            Log.e("", "res, error = $result, $error")
            when (error) {
                null -> {
                    reservationsListState.postValue(ReservationsListState.Success(result))
                }
                else -> {
                    reservationsListState.postValue(ReservationsListState.Error(error))
                }
            }
        }
    }
}
