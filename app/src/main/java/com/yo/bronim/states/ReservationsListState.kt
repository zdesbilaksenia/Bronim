package com.yo.bronim.states

import com.yo.bronim.models.Reservation
import com.yo.bronim.models.ReservationListItem

sealed class ReservationsListState {
    object Pending : ReservationsListState()
    class Success(val result: Array<ReservationListItem>) : ReservationsListState()
    class Error(val error: Throwable) : ReservationsListState()
}
