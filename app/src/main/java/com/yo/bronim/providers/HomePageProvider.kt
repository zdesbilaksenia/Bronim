package com.yo.bronim.providers

import android.util.Log
import com.yo.bronim.models.Restaurant as Restaurant
import com.yo.bronim.repository.HomePageRepository as HomePageRepository
import java.io.IOException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

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

    fun getPopularRestaurants(
        callback: (
            result: Array<Restaurant>?,
            error: Throwable?
        ) -> Unit
    ) {
        scope.launch {
            try {
                val result = homePageRepository.getPopularRestaurants()
                invokeCallback(callback, result, null)
            } catch (error: IOException) {
                invokeCallback(callback, null, error)
            }
        }
    }

    fun getNewRestaurants(callback: (result: Array<Restaurant>?, error: Throwable?) -> Unit) {
        scope.launch {
            try {
                val result = homePageRepository.getNewRestaurants()
                invokeCallback(callback, result, null)
            } catch (error: IOException) {
                invokeCallback(callback, null, error)
            }
        }
    }

    fun getNearestRestaurants(callback: (result: Array<Restaurant>?, error: Throwable?) -> Unit) {
        scope.launch {
            try {
                val result = homePageRepository.getNearestRestaurants()
                invokeCallback(callback, result, null)
            } catch (error: IOException) {
                invokeCallback(callback, null, error)
            }
        }
    }

    fun cuisineFiltration(
        callback: (result: Array<Restaurant>?, error: Throwable?) -> Unit,
        cuisine: String
    ) {
        scope.launch {
            try {
                Log.e("", "provider requested")
                val result = homePageRepository.cuisineFiltration(cuisine)
                invokeCallback(callback, result, null)
            } catch (error: Throwable) {
                invokeCallback(callback, null, error)
            }
        }
    }
}
