package com.yo.bronim.states

import com.yo.bronim.models.Reservation

sealed class ReservationsListState {
    object Pending : ReservationsListState()
    class Success(val result: Array<Reservation>) : ReservationsListState()
    class Error(val error: Throwable) : ReservationsListState()
}
