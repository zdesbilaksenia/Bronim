package com.yo.bronim.managers

import com.yo.bronim.models.Restaurant
import com.yo.bronim.providers.HomePageProvider

class HomePageManager {
    private val homePageProvider = HomePageProvider()

    fun getPopularRestaurants(
        callback: (
            result: Array<Restaurant>?,
            error: Throwable?
        ) -> Unit
    ) {
        homePageProvider.getPopularRestaurants { result, error ->
            callback(result, error)
        }
    }

    fun getNewRestaurants(callback: (result: Array<Restaurant>?, error: Throwable?) -> Unit) {
        homePageProvider.getNewRestaurants { result, error ->
            callback(result, error)
        }
    }

    fun getNearestRestaurants(callback: (result: Array<Restaurant>?, error: Throwable?) -> Unit) {
        homePageProvider.getNearestRestaurants { result, error ->
            callback(result, error)
        }
    }
}
