package com.yo.bronim.repository

import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.yo.bronim.interfaces.ReservationApi
import com.yo.bronim.models.ReservationListItem
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
        val user = Firebase.auth.currentUser
        if (user != null) {
            return reservationApi.getReservationsList(user.uid).body()?.restaurantReservationList
        }
        return null
//        return reservations
    }
}

var reservations: Array<ReservationListItem> = arrayOf(
    ReservationListItem(
        id = 1,
        name = "Sempre",
        address = "улица такая дом такой",
        tags = listOf("lala", "gaga"),
        date = "19.04",
        time = "23:46",
        guests_num = 6,
    ),
    ReservationListItem(
        id = 1,
        name = "Sempre",
        address = "улица такая дом такой",
        tags = listOf("lala", "gaga"),
        date = "19.04",
        time = "23:46",
        guests_num = 6,
    ),
    ReservationListItem(
        id = 1,
        name = "Sempre",
        address = "улица такая дом такой",
        tags = listOf("lala", "gaga"),
        date = "19.04",
        time = "23:46",
        guests_num = 6,
    ),
    ReservationListItem(
        id = 1,
        name = "Sempre",
        address = "улица такая дом такой",
        tags = listOf("lala", "gaga"),
        date = "19.04",
        time = "23:46",
        guests_num = 6,
    ),
    ReservationListItem(
        id = 1,
        name = "Sempre",
        address = "улица такая дом такой",
        tags = listOf("lala", "gaga"),
        date = "19.04",
        time = "23:46",
        guests_num = 6,
    ),
)
