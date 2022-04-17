package com.yo.bronim.managers

import com.yo.bronim.models.Restaurant
import com.yo.bronim.providers.RestaurantPageProvider

class RestaurantPageManager {
    private val restaurantPageProvider = RestaurantPageProvider()

    fun getRestaurant(
        callback: (result: Restaurant?, error: Throwable?) -> Unit,
        restaurantID: Int
    ) {
        restaurantPageProvider.getRestaurant(
            { result, error -> callback(result, error) },
            restaurantID
        )
    }
}
