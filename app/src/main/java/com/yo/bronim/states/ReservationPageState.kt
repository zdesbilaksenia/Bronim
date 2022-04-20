package com.yo.bronim.states

import com.yo.bronim.models.Reservation
import com.yo.bronim.models.Restaurant

sealed class ReservationPageState {
    class Pending : ReservationPageState()
    class Success(val result: Array<Reservation>) : ReservationPageState()
    class Error(val error: Throwable) : ReservationPageState()
}