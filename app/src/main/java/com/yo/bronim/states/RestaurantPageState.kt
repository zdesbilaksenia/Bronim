package com.yo.bronim.states

import com.yo.bronim.models.Restaurant

sealed class RestaurantPageState {
    class Pending : RestaurantPageState()
    class Success(val result: Restaurant) : RestaurantPageState()
    class Error(val error: Throwable) : RestaurantPageState()
}
