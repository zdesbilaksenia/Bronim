package com.yo.bronim.providers

import com.yo.bronim.models.Restaurant as Restaurant
import com.yo.bronim.repository.RestaurantPageRepository as RestaurantPageRepository
import java.io.IOException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class RestaurantPageProvider {
    private val scope = CoroutineScope(Dispatchers.IO)
    private val restaurantPageRepository = RestaurantPageRepository()

    private suspend fun invokeCallback(
        callback: (result: Restaurant?, error: Throwable?) -> Unit,
        result: Restaurant?,
        error: Throwable?
    ) {
        withContext(Dispatchers.Main) {
            callback(result, error)
        }
    }

    fun getRestaurant(
        callback: (result: Restaurant?, error: Throwable?) -> Unit,
        restaurantID: Int?
    ) {
        scope.launch {
            try {
                val result = restaurantPageRepository.getRestaurant(restaurantID)
                invokeCallback(callback, result, null)
            } catch (error: IOException) {
                invokeCallback(callback, null, error)
            }
        }
    }
}
