package com.yo.bronim.repository

import android.util.Log
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.yo.bronim.interfaces.ReservationApi
import com.yo.bronim.models.RestaurantReservation
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class ReservationsListPageRepository {
    private val client = OkHttpClient.Builder().build()
    private val retrofit = Retrofit.Builder()
        .client(client)
        .baseUrl("https://bmstusa.ru/")
        .addConverterFactory(MoshiConverterFactory.create().asLenient())
        .build()
    private val reservationApi = retrofit.create(ReservationApi::class.java)

    suspend fun getReservationsList(): Array<RestaurantReservation>? {
        Log.d("[RESERV_LIST_REPO]", "start getResrvList")
        val user = Firebase.auth.currentUser
        if (user != null) {
            Log.d("[RESERV_LIST_REPO]", "gettin getResrvList")
            val body = reservationApi.getReservationsList(user.uid).body()
            Log.d("[RESERV_LIST_REPO]", "got getResrvList")
            Log.d("[RESERV_LIST_REPO]", body.toString())
            return body?.restaurantReservationList
        }
        return null
    }
}
