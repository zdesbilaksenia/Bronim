package com.yo.bronim.repository

import android.util.Log
import com.yo.bronim.interfaces.ReservationApi
import com.yo.bronim.interfaces.RestaurantApi
import com.yo.bronim.models.PostReservation
import com.yo.bronim.models.Reservation
import com.yo.bronim.models.Restaurant
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class ReservationPageRepository() {
    private val client = OkHttpClient.Builder().build()
    private val retrofit = Retrofit.Builder()
        .client(client)
        .baseUrl("https://bmstusa.ru/")
        .addConverterFactory(MoshiConverterFactory.create().asLenient())
        .build()
    private val reservationApi = retrofit.create(ReservationApi::class.java)

    suspend fun getAvailableTablesAndTime(
        restId: Int,
        date: String,
        numOfGuests: Int
    ): Array<Reservation>? {
        return reservationApi.getAvailableTablesAndTime(restId, date, numOfGuests)
            .body()?.reservations
    }

    suspend fun sendReservationInfo(
        postReservation: PostReservation,
        restId: Int,
        chosenTable: Int
    ) {
        Log.d(
            "GOT",
            "${reservationApi.sendReservationInfo(postReservation, restId, chosenTable).body()}"
        )
    }
}