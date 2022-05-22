package com.yo.bronim.managers

import com.yo.bronim.models.Restaurant
import com.yo.bronim.providers.FavouritesPageProvider

class FavouritesPageManager {
    private val favouritesPageProvider = FavouritesPageProvider()

    fun getFavouritesRestaurants(
        callback: (result: Array<Restaurant>?, error: Throwable?) -> Unit
    ) {
        favouritesPageProvider.getFavouritesRestaurants { result, error ->
            callback(result, error)
        }
    }

    fun subscribe(callback: (error: Throwable?) -> Unit, restaurantID: Int?) {
        favouritesPageProvider.subscribe(callback, restaurantID)
    }

    fun unsubscribe(callback: (error: Throwable?) -> Unit, restaurantID: Int?) {
        favouritesPageProvider.unsubscribe(callback, restaurantID)
    }
}
