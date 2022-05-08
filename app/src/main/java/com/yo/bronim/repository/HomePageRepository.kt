package com.yo.bronim.repository

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
}

val restaurants = arrayOf(
    Restaurant(
        id = 1,
        name = "Sempre",
        address = "tuda suda",
        description = "lalala",
        img = "",
        phone = "89686035903",
        email = "some@mail.ru",
        website = "lala.ru",
        tags = listOf("lala", "gaga"),
        rating = 3.4F,
        start = 1,
        end = 10,
    ),
    Restaurant(
        id = 1,
        name = "Sempre",
        address = "tuda suda",
        description = "lalala",
        img = "",
        phone = "89686035903",
        email = "some@mail.ru",
        website = "lala.ru",
        tags = listOf("lala", "gaga"),
        rating = 3.4F,
        start = 1,
        end = 10,
    ),
    Restaurant(
        id = 1,
        name = "Sempre",
        address = "tuda suda",
        description = "lalala",
        img = "",
        phone = "89686035903",
        email = "some@mail.ru",
        website = "lala.ru",
        tags = listOf("lala", "gaga"),
        rating = 3.4F,
        start = 1,
        end = 10,
    ),
    Restaurant(
        id = 1,
        name = "Sempre",
        address = "tuda suda",
        description = "lalala",
        img = "",
        phone = "89686035903",
        email = "some@mail.ru",
        website = "lala.ru",
        tags = listOf("lala", "gaga"),
        rating = 3.4F,
        start = 1,
        end = 10,
    ),
)
