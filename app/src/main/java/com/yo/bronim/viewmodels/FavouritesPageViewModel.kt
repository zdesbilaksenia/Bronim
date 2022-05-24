package com.yo.bronim.viewmodels

import androidx.lifecycle.MutableLiveData
import com.yo.bronim.managers.FavouritesPageManager
import com.yo.bronim.states.FavouritesPageState
import com.yo.bronim.states.SubscribeState

class FavouritesPageViewModel {
    private val favouritesPageManager = FavouritesPageManager()
    val favouritesRestaurantsState = MutableLiveData<FavouritesPageState>()
    val subscribeState = MutableLiveData<SubscribeState>()
    val unsubscribeState = MutableLiveData<SubscribeState>()

    fun getFavouritesRestaurants() {
        favouritesRestaurantsState.postValue(FavouritesPageState.Pending())

        favouritesPageManager.getFavouritesRestaurants { result, error ->
            when {
                result != null -> {
                    favouritesRestaurantsState.postValue(FavouritesPageState.Success(result))
                }
                error != null -> {
                    favouritesRestaurantsState.postValue(FavouritesPageState.Error(error))
                }
            }
        }
    }

    fun subscribe(restaurantID: Int?) {
        subscribeState.postValue(SubscribeState.Pending())

        favouritesPageManager.subscribe(
            { error ->
                when {
                    error == null -> {
                        subscribeState.postValue(SubscribeState.Success())
                    }
                    else -> {
                        subscribeState.postValue(SubscribeState.Error(error))
                    }
                }
            },
            restaurantID
        )
    }

    fun unsubscribe(restaurantID: Int?) {
        unsubscribeState.postValue(SubscribeState.Pending())

        favouritesPageManager.unsubscribe(
            { error ->
                when {
                    error == null -> {
                        unsubscribeState.postValue(SubscribeState.Success())
                    }
                    else -> {
                        unsubscribeState.postValue(SubscribeState.Error(error))
                    }
                }
            },
            restaurantID
        )
    }
}
