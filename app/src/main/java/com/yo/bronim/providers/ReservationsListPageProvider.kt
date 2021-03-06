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

    private fun cellNumToTime(i: Int): String {
        return "${(i / 2).toString().padStart(2, '0')}:${(i % 2 * 30).toString().padStart(2, '0')}"
    }

    private fun cellsToTimes(cells: MutableList<Int>): ArrayList<String> {
        cells.add(cells[cells.size - 1] + 1)
        val times = ArrayList<String>()
        var start = cells[0]
        var finish: Int
        for (i in 1 until cells.size) {
            if (cells[i] - cells[i - 1] > 1) {
                finish = cells[i - 1] + 1
                times.add(cellNumToTime(start) + " - " + cellNumToTime(finish))
                start = cells[i]
            }
        }
        finish = cells[cells.size - 1]
        times.add(cellNumToTime(start) + " - " + cellNumToTime(finish))
        return times
    }

    private fun dateToNormalDate(date: String): String {
        val dates = date.split('-')
        var res = ""
        for (d in dates) {
            res = ".$d$res"
        }
        return res.slice(1 until res.length)
    }

    fun getReservationsList(callback: ReservationsListCallback) {
        scope.launch {
            try {
                val restaurantReservationList = reservationsListPageRepository.getReservationsList()
                if (restaurantReservationList == null) {
                    invokeCallback(callback, null, null)
                    return@launch
                }
                val result = ArrayList<ReservationListItem>()
                for (rr in restaurantReservationList) {
                    val cells: MutableList<Int> = rr.reservation.cells
                    cells.sort()
                    val times = cellsToTimes(cells)
                    for (t in times) {
                        val reservation = ReservationListItem(
                            id = 0,
                            name = rr.restaurant.name,
                            address = rr.restaurant.address,
                            tags = rr.restaurant.tags,
                            date = dateToNormalDate(rr.reservation.date),
                            time = t,
                            guests = rr.reservation.guests,
                            imgSrc = rr.restaurant.img,
                        )
                        result.add(reservation)
                    }
                }
                invokeCallback(callback, result.toTypedArray(), null)
            } catch (error: Throwable) {
                invokeCallback(callback, null, error)
            }
        }
    }
}
