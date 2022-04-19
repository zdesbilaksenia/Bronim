package com.yo.bronim.viewmodels

import androidx.lifecycle.MutableLiveData
import com.yo.bronim.managers.HomePageManager
import com.yo.bronim.states.HomePageState

class HomePageViewModel {
    private val homePageManager = HomePageManager()
    val recommendedRestaurantsState = MutableLiveData<HomePageState>()
    val newRestaurantsState = MutableLiveData<HomePageState>()
    val nearestRestaurantsState = MutableLiveData<HomePageState>()

    fun getPopularRestaurants() {
        recommendedRestaurantsState.postValue(HomePageState.Pending())

        homePageManager.getPopularRestaurants { result, error ->
            when {
                result != null -> {
                    recommendedRestaurantsState.postValue(HomePageState.Success(result))
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
                    newRestaurantsState.postValue(HomePageState.Success(result))
                }
                error != null -> {
                    newRestaurantsState.postValue(HomePageState.Error(error))
                }
            }
        }
    }

    fun getNearestRestaurants() {
        nearestRestaurantsState.postValue(HomePageState.Pending())

        homePageManager.getNearestRestaurants { result, error ->
            when {
                result != null -> {
                    nearestRestaurantsState.postValue(HomePageState.Success(result))
                }
                error != null -> {
                    nearestRestaurantsState.postValue(HomePageState.Error(error))
                }
            }
        }
    }
}
