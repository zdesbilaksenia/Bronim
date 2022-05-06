package com.yo.bronim.repository

import com.yo.bronim.interfaces.RestaurantApi
import com.yo.bronim.models.Restaurant
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class RestaurantPageRepository {
    private val client = OkHttpClient.Builder().build()
    private val retrofit = Retrofit.Builder()
        .client(client)
        .baseUrl("https://bmstusa.ru/")
        .addConverterFactory(MoshiConverterFactory.create().asLenient())
        .build()
    private val restaurantApi = retrofit.create(RestaurantApi::class.java)

    suspend fun getRestaurant(restaurantID: Int?): Restaurant? {
        return restaurantApi.getRestaurant(restaurantID).body()
    }
}
