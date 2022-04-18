package com.yo.bronim.interfaces

import com.yo.bronim.models.RestaurantList
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers

interface RestaurantApi {
    @Headers("Content-Type: application/json")
    @GET("/bronim/restaurants/popular")
    suspend fun getPopularRestaurants(): Response<RestaurantList>

    @Headers("Content-Type: application/json")
    @GET("/bronim/restaurants/nearest")
    suspend fun getNearestRestaurants(): Response<RestaurantList>

    @Headers("Content-Type: application/json")
    @GET("/bronim/restaurants/new")
    suspend fun getNewRestaurants(): Response<RestaurantList>
}
