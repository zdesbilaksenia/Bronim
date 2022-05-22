package com.yo.bronim.providers

import com.yo.bronim.models.ReservationListItem as ReservationListItem
import com.yo.bronim.models.ReservationsListCallback
import com.yo.bronim.repository.ReservationsListPageRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

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
//                val restaurantReservationList = reservationsListPageRepository.getReservationsList()
//                    ?: throw Exception(R.string.errorUserIsNotLoggedIn.toString())
//                val result = ArrayList<ReservationListItem>()
//                for (rr in restaurantReservationList) {
// //                    todo
//                    val time = "02:53"
//                    val reservation = ReservationListItem(
//                        id = 0,
//                        name = rr.restaurant.name,
//                        address = rr.restaurant.address,
//                        tags = rr.restaurant.tags,
//                        date = rr.reservation.date,
//                        time = time,
//                        guests = rr.reservation.guests,
//                    )
//                    result.add(reservation)
//                }
//                invokeCallback(callback, result.toTypedArray(), null)
                invokeCallback(callback, reservations, null)
            } catch (error: Throwable) {
                invokeCallback(callback, null, error)
            }
        }
    }
}

var reservations: Array<ReservationListItem> = arrayOf(
    ReservationListItem(
        id = 1,
        name = "Sempre",
        address = "улица такая дом такой",
        tags = listOf("lala", "gaga"),
        date = "19.04",
        time = "23:46",
        guests = 6,
    ),
    ReservationListItem(
        id = 1,
        name = "Sempre",
        address = "улица такая дом такой",
        tags = listOf("lala", "gaga"),
        date = "19.04",
        time = "23:46",
        guests = 6,
    ),
    ReservationListItem(
        id = 1,
        name = "Sempre",
        address = "улица такая дом такой",
        tags = listOf("lala", "gaga"),
        date = "19.04",
        time = "23:46",
        guests = 6,
    ),
    ReservationListItem(
        id = 1,
        name = "Sempre",
        address = "улица такая дом такой",
        tags = listOf("lala", "gaga"),
        date = "19.04",
        time = "23:46",
        guests = 6,
    ),
    ReservationListItem(
        id = 1,
        name = "Sempre",
        address = "улица такая дом такой",
        tags = listOf("lala", "gaga"),
        date = "19.04",
        time = "23:46",
        guests = 6,
    ),
)
