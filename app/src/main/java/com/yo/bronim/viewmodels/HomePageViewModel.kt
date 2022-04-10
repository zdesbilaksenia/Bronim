package com.yo.bronim.viewmodels

import androidx.lifecycle.MutableLiveData
import com.yo.bronim.managers.HomePageManager
import com.yo.bronim.states.HomePageState

class HomePageViewModel {
    private val homePageManager = HomePageManager()
    val recommendedRestaurantsState = MutableLiveData<HomePageState>()
    val newRestaurantsState = MutableLiveData<HomePageState>()

    fun getRecommendedRestaurants() {
        recommendedRestaurantsState.postValue(HomePageState.Pending())

        homePageManager.getRecommendedRestaurants { result, error ->
            when {
                result != null -> {
                    recommendedRestaurantsState.postValue(HomePageState.Succes(result))
                }
                error != null -> {
                    recommendedRestaurantsState.postValue(HomePageState.Error(error))
                }
            }
        }
    }

    fun getNewRestaurants() {
        newRestaurantsState.postValue(HomePageState.Pending())

        homePageManager.getNewRestaurants { result, error ->
            when {
                result != null -> {
                    newRestaurantsState.postValue(HomePageState.Succes(result))
                }
                error != null -> {
                    newRestaurantsState.postValue(HomePageState.Error(error))
                }
            }
        }
    }
}