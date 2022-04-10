package com.yo.bronim.providers

import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import com.yo.bronim.repository.MainPageRepository as HomePageRepository
import com.yo.bronim.models.Restaurant as Restaurant

class HomePageProvider {
    private val scope = CoroutineScope(Dispatchers.IO)
    private val homePageRepository = HomePageRepository()

    private suspend fun invokeCallback(
        callback: (result: Array<Restaurant>?, error: Throwable?) -> Unit,
        result: Array<Restaurant>?,
        error: Throwable?
    ) {
        withContext(Dispatchers.Main) {
            callback(result, error)
        }
    }

    fun getRecommendedRestaurants(callback: (result: Array<Restaurant>?, error: Throwable?) -> Unit) {
        scope.launch {
            try {
                val result = homePageRepository.getRecommendedRestaurants()
                invokeCallback(callback, result, null)
            } catch (error: Throwable) {
                invokeCallback(callback, null, error)
            }
        }
    }

    fun getNewRestaurants(callback: (result: Array<Restaurant>?, error: Throwable?) -> Unit) {
        scope.launch {
            try {
                val result = homePageRepository.getNewRestaurants()
                invokeCallback(callback, result, null)
            } catch (error: Throwable) {
                invokeCallback(callback, null, error)
            }
        }
    }
}