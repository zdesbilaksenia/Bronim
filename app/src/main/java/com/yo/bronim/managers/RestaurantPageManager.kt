package com.yo.bronim.managers

import android.util.Log
import com.yo.bronim.models.Restaurant
import com.yo.bronim.providers.RestaurantPageProvider

class RestaurantPageManager {
    private val restaurantPageProvider = RestaurantPageProvider()

    fun getRestaurant(
        callback: (result: Restaurant?, error: Throwable?) -> Unit,
        restaurantID: Int?
    ) {
        Log.d("FUCCCKK mang", restaurantID.toString())
        restaurantPageProvider.getRestaurant(
            { result, error -> callback(result, error) },
            restaurantID
        )
    }
}
