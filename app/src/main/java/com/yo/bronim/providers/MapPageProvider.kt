package com.yo.bronim.providers

import com.yo.bronim.models.Restaurant
import com.yo.bronim.repository.MapPageRepository
import java.io.IOException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MapPageProvider {
    private val scope = CoroutineScope(Dispatchers.IO)
    private val mapPageRepository = MapPageRepository()

    private suspend fun invokeCallback(
        callback: (result: Array<Restaurant>?, error: Throwable?) -> Unit,
        result: Array<Restaurant>?,
        error: Throwable?
    ) {
        withContext(Dispatchers.Main) {
            callback(result, error)
        }
    }

    fun getRestaurants(
        callback: (
            result: Array<Restaurant>?,
            error: Throwable?
        ) -> Unit
    ) {
        scope.launch {
            try {
                val result = mapPageRepository.getRestaurants()
                invokeCallback(callback, result, null)
            } catch (error: IOException) {
                invokeCallback(callback, null, error)
            }
        }
    }
}
