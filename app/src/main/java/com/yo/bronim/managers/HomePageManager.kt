package com.yo.bronim.managers

import com.yo.bronim.models.Restaurant
import com.yo.bronim.providers.HomePageProvider

class HomePageManager {
    private val homePageProvider = HomePageProvider()

    fun getRecommendedRestaurants(callback: (result: Array<Restaurant>?, error: Throwable?) -> Unit) {
        homePageProvider.getRecommendedRestaurants { result, error ->
            callback(result, error)
        }
    }

    fun getNewRestaurants(callback: (result: Array<Restaurant>?, error: Throwable?) -> Unit) {
        homePageProvider.getNewRestaurants { result, error ->
            callback(result, error)
        }
    }
}