package com.yo.bronim.interfaces

import com.yo.bronim.models.RestaurantReservationList
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path

interface ReservationApi {
    @Headers("Content-Type: application/json")
    @GET("/bronim/{id]/reservations")
    suspend fun getReservationsList(
        @Path("id") id: String,
    ): Response<RestaurantReservationList>
}