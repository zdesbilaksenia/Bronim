package com.yo.bronim.viewmodels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.yo.bronim.managers.HomePageManager
import com.yo.bronim.states.CuisineFiltrationState
import com.yo.bronim.states.HomePageState

class HomePageViewModel {
    private val homePageManager = HomePageManager()
    val recommendedRestaurantsState = MutableLiveData<HomePageState>()
    val newRestaurantsState = MutableLiveData<HomePageState>()
    val nearestRestaurantsState = MutableLiveData<HomePageState>()
    val cuisineFiltrationState = MutableLiveData<CuisineFiltrationState>()

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

    fun cuisineFiltration(cuisine: String) {
        Log.e("", "vm requested")
        cuisineFiltrationState.postValue(CuisineFiltrationState.Pending)

        homePageManager.cuisineFiltration(
            { result, error ->
                when {
                    result != null -> {
                        cuisineFiltrationState.postValue(CuisineFiltrationState.Success(result))
                    }
                    error != null -> {
                        cuisineFiltrationState.postValue(CuisineFiltrationState.Error(error))
                    }
                }
            },
            cuisine
        )
    }
}
