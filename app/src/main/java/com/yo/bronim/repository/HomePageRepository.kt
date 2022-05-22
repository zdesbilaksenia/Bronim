package com.yo.bronim.repository

import android.util.Log
import com.yo.bronim.interfaces.RestaurantApi
import com.yo.bronim.models.Restaurant
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class HomePageRepository {
    private val client = OkHttpClient.Builder().build()
    private val retrofit = Retrofit.Builder()
        .client(client)
        .baseUrl("https://bmstusa.ru/")
        .addConverterFactory(MoshiConverterFactory.create().asLenient())
        .build()
    private val restaurantApi = retrofit.create(RestaurantApi::class.java)

    suspend fun getPopularRestaurants(): Array<Restaurant>? {
        return restaurantApi.getPopularRestaurants().body()?.restaurants
    }

    suspend fun getNearestRestaurants(): Array<Restaurant>? {
        return restaurantApi.getNearestRestaurants().body()?.restaurants
    }

    suspend fun getNewRestaurants(): Array<Restaurant>? {
        return restaurantApi.getNewRestaurants().body()?.restaurants
    }

    suspend fun cuisineFiltration(cuisine: String): Array<Restaurant>? {
        Log.e("", "repo requested for $cuisine")
        val r = restaurantApi.cuisineFiltration(cuisine).body()?.restaurants
        Log.e("", "repo got $r")
        return r
    }
}
