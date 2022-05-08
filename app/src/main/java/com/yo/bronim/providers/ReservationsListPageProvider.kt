package com.yo.bronim.providers

import com.yo.bronim.R
import com.yo.bronim.models.Reservation
import com.yo.bronim.models.ReservationsListCallback
import com.yo.bronim.repository.ReservationsListPageRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Error
import com.yo.bronim.models.ReservationListItem as ReservationListItem

class ReservationsListPageProvider {
    private val scope = CoroutineScope(Dispatchers.IO)
    private val reservationsListPageRepository = ReservationsListPageRepository()

    private suspend fun invokeCallback(
        callback: ReservationsListCallback,
        result: Array<ReservationListItem>?,
        error: Throwable?
    ) {
        withContext(Dispatchers.Main) {
            callback(result, error)
        }
    }

    fun getReservationsList(callback: ReservationsListCallback) {
        scope.launch {
            try {
                val restaurantReservationList = reservationsListPageRepository.getReservationsList()
                    ?: throw Exception(R.string.errorUserIsNotLoggedIn.toString())
                val result = ArrayList<ReservationListItem>()
                for (rr in restaurantReservationList) {
//                    todo
                    val time = "02:53"
                    val reservation = ReservationListItem(
                        id = rr.reservation.id,
                        name = rr.restaurant.name,
                        address = rr.restaurant.address,
                        tags = rr.restaurant.tags,
                        date = rr.reservation.date,
                        time = time,
                        guests_num = rr.reservation.guests_num,
                    )
                    result.add(reservation)
                }
                invokeCallback(callback, result.toTypedArray(), null)
//                invokeCallback(callback, reservations, null)
            } catch (error: Throwable) {
                invokeCallback(callback, null, error)
            }
        }
    }
}

var reservations : Array<ReservationListItem> = arrayOf(
    ReservationListItem(
        id = 1,
        name = "Sempre",
        address = "улица такая дом такой",
        tags = listOf("lala", "gaga"),
        date = "19.04",
        time = "23:46",
        guests_num = 6,
    ),
    ReservationListItem(
        id = 1,
        name = "Sempre",
        address = "улица такая дом такой",
        tags = listOf("lala", "gaga"),
        date = "19.04",
        time = "23:46",
        guests_num = 6,
    ),
    ReservationListItem(
        id = 1,
        name = "Sempre",
        address = "улица такая дом такой",
        tags = listOf("lala", "gaga"),
        date = "19.04",
        time = "23:46",
        guests_num = 6,
    ),
    ReservationListItem(
        id = 1,
        name = "Sempre",
        address = "улица такая дом такой",
        tags = listOf("lala", "gaga"),
        date = "19.04",
        time = "23:46",
        guests_num = 6,
    ),
    ReservationListItem(
        id = 1,
        name = "Sempre",
        address = "улица такая дом такой",
        tags = listOf("lala", "gaga"),
        date = "19.04",
        time = "23:46",
        guests_num = 6,
    ),
)
