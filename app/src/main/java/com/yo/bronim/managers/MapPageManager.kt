package com.yo.bronim.managers

import com.yo.bronim.models.Restaurant
import com.yo.bronim.providers.MapPageProvider

class MapPageManager {
    private val mapPageProvider = MapPageProvider()

    fun getRestaurants(
        callback: (
            result: Array<Restaurant>?,
            error: Throwable?
        ) -> Unit
    ) {
        mapPageProvider.getRestaurants { result, error ->
            callback(result, error)
        }
    }
}
