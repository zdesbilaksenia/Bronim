package com.yo.bronim.states

import com.yo.bronim.models.Reservation

sealed class ReservationPageState {
    class Pending : ReservationPageState()
    class Success(val result: Array<Reservation>?) : ReservationPageState()
    class Error(val error: Throwable) : ReservationPageState()
}
