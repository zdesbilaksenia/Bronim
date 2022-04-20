package com.yo.bronim.interfaces

import com.yo.bronim.models.PostReservation
import com.yo.bronim.models.ReservationsList
import com.yo.bronim.models.RestaurantList
import retrofit2.Response
import retrofit2.http.*

interface ReservationApi {
    @Headers("Content-Type: application/json")
    @GET("/bronim/restaurants/{id}/reservations")
    suspend fun getAvailableTablesAndTime(
        @Path("id") restaurantId: Int,
        @Query("date") date: String,
        @Query("guests") numOfGuests: Int
    ): Response<ReservationsList>


    @Headers("Content-Type: application/json")
    @POST("/bronim/restaurants/{id}/tables/{table}")
    suspend fun sendReservationInfo(
        @Body postReservation: PostReservation,
        @Path("id") restaurantId: Int,
        @Path("table") table: Int
    ): Response<PostReservation>
}