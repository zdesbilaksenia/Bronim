package com.yo.bronim.repository

import com.yo.bronim.models.Restaurant

class RestaurantPageRepository {
    fun getRestaurant(restaurantID: Int): Restaurant {
        return restaurant
    }

    companion object {
        private val restaurant =
            Restaurant(
                1,
                "2",
                "a",
                "b",
                "c",
                "d",
                "e",
                "f",
                listOf("i", "j"),
                10f
            )
    }
}
