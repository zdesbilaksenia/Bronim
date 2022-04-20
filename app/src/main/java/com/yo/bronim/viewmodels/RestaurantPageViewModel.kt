package com.yo.bronim.viewmodels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.yo.bronim.managers.RestaurantPageManager
import com.yo.bronim.states.RestaurantPageState

class RestaurantPageViewModel {
    private val restaurantPageManager = RestaurantPageManager()
    val restaurantState = MutableLiveData<RestaurantPageState>()

    fun getRestaurant(restaurantID: Int?) {
        Log.d("FUCCCKK viewm", restaurantID.toString())
        restaurantState.postValue(RestaurantPageState.Pending())

        restaurantPageManager.getRestaurant( { result, error ->
            when {
                result != null -> {
                    restaurantState.postValue(RestaurantPageState.Success(result))
                }
                error != null -> {
                    restaurantState.postValue(RestaurantPageState.Error(error))
                }
            }
        },
            restaurantID
        )
    }
}
