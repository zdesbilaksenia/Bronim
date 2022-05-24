package com.yo.bronim.interfaces

import com.yo.bronim.models.Restaurant
import com.yo.bronim.models.RestaurantList
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

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

    @Headers("Content-Type: application/json")
    @GET("/bronim/restaurants/{id}")
    suspend fun getRestaurant(@Path("id") id: Int?): Response<Restaurant>

    @Headers("Content-Type: application/json")
    @GET("/bronim/restaurants")
    suspend fun cuisineFiltration(@Query("cuisine") cuisine: String?): Response<RestaurantList>

    @GET("/bronim/profiles/{id}/favourites")
    suspend fun getFavouritesRestaurants(@Path("id") id: String?): Response<RestaurantList>

    @Headers("Content-Type: application/json")
    @GET("/bronim/profiles/{userid}/subscribe/{restid}")
    suspend fun subscribe(@Path("userid") userid: String?, @Path("restid") restid: Int?)

    @Headers("Content-Type: application/json")
    @GET("/bronim/profiles/{userid}/unsubscribe/{restid}")
    suspend fun unsubscribe(@Path("userid") userid: String?, @Path("restid") restid: Int?)
}
