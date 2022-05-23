package com.yo.bronim.viewmodels

import androidx.lifecycle.MutableLiveData
import com.yo.bronim.managers.MapPageManager
import com.yo.bronim.states.MapPageState

class MapPageViewModel {
    private val mapPageManager = MapPageManager()
    val restaurantsState = MutableLiveData<MapPageState>()

    fun getRestaurants() {
        restaurantsState.postValue(MapPageState.Pending())

        mapPageManager.getRestaurants { result, error ->
            when {
                result != null -> {
                    restaurantsState.postValue(MapPageState.Success(result))
                }
                error != null -> {
                    restaurantsState.postValue(MapPageState.Error(error))
                }
            }
        }
    }
}
