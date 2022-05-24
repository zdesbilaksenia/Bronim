package com.yo.bronim.providers

import com.yo.bronim.models.Restaurant
import com.yo.bronim.repository.AuthorizationPageRepository
import com.yo.bronim.repository.FavouritesPageRepository
import java.io.IOException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FavouritesPageProvider {
    private val scope = CoroutineScope(Dispatchers.IO)
    private val favouritesPageRepository = FavouritesPageRepository()
    private val authorizationPageRepository = AuthorizationPageRepository()

    private suspend fun invokeCallback(
        callback: (result: Array<Restaurant>?, error: Throwable?) -> Unit,
        result: Array<Restaurant>?,
        error: Throwable?
    ) {
        withContext(Dispatchers.Main) {
            callback(result, error)
        }
    }

    private suspend fun invokeErrorCallback(
        callback: (error: Throwable?) -> Unit,
        error: Throwable?
    ) {
        withContext(Dispatchers.Main) {
            callback(error)
        }
    }

    fun getFavouritesRestaurants(
        callback: (result: Array<Restaurant>?, error: Throwable?) -> Unit
    ) {
        scope.launch {
            try {
                val firebaseID = authorizationPageRepository.getFID()
                val result = favouritesPageRepository.getFavouritesRestaurants(firebaseID)
                invokeCallback(callback, result, null)
            } catch (error: IOException) {
                invokeCallback(callback, null, error)
            }
        }
    }

    fun subscribe(callback: (error: Throwable?) -> Unit, restaurantID: Int?) {
        scope.launch {
            try {
                val firebaseID = authorizationPageRepository.getFID()
                favouritesPageRepository.subscribe(firebaseID, restaurantID)
                invokeErrorCallback(callback, null)
            } catch (error: IOException) {
                invokeErrorCallback(callback, error)
            }
        }
    }

    fun unsubscribe(callback: (error: Throwable?) -> Unit, restaurantID: Int?) {
        scope.launch {
            try {
                val firebaseID = authorizationPageRepository.getFID()
                favouritesPageRepository.unsubscribe(firebaseID, restaurantID)
                invokeErrorCallback(callback, null)
            } catch (error: IOException) {
                invokeErrorCallback(callback, error)
            }
        }
    }
}
