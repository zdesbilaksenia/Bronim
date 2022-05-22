package com.yo.bronim.repository

import com.yo.bronim.interfaces.RestaurantApi
import com.yo.bronim.models.Restaurant
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class FavouritesPageRepository {
    private val client = OkHttpClient.Builder().build()
    private val retrofit = Retrofit.Builder()
        .client(client)
        .baseUrl("https://bmstusa.ru/")
        .addConverterFactory(MoshiConverterFactory.create().asLenient())
        .build()
    private val restaurantApi = retrofit.create(RestaurantApi::class.java)

    suspend fun getFavouritesRestaurants(firebaseID: String?): Array<Restaurant>? {
        return restaurantApi.getFavouritesRestaurants(firebaseID).body()?.restaurants
    }

    suspend fun subscribe(firebaseID: String?, restaurantID: Int?) {
        restaurantApi.subscribe(firebaseID, restaurantID)
    }

    suspend fun unsubscribe(firebaseID: String?, restaurantID: Int?) {
        restaurantApi.unsubscribe(firebaseID, restaurantID)
    }
}
