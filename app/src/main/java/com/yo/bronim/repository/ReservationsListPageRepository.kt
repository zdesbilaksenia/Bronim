package com.yo.bronim.repository

import com.yo.bronim.interfaces.ReservationApi
import com.yo.bronim.models.Reservation
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

    suspend fun getReservationsList() : Array<Reservation> {
        return reservationApi.getReservationsList()
//        return reservations
    }
}

var reservations : Array<Reservation> = arrayOf(
    Reservation(
        id = 1,
        name = "Sempre",
        address = "улица такая дом такой",
        tags = listOf("lala", "gaga"),
        date = "19.04",
        time = "23:46",
        guests_num = 6,
    ),
    Reservation(
        id = 1,
        name = "Sempre",
        address = "улица такая дом такой",
        tags = listOf("lala", "gaga"),
        date = "19.04",
        time = "23:46",
        guests_num = 6,
    ),
    Reservation(
        id = 1,
        name = "Sempre",
        address = "улица такая дом такой",
        tags = listOf("lala", "gaga"),
        date = "19.04",
        time = "23:46",
        guests_num = 6,
    ),
    Reservation(
        id = 1,
        name = "Sempre",
        address = "улица такая дом такой",
        tags = listOf("lala", "gaga"),
        date = "19.04",
        time = "23:46",
        guests_num = 6,
    ),
    Reservation(
        id = 1,
        name = "Sempre",
        address = "улица такая дом такой",
        tags = listOf("lala", "gaga"),
        date = "19.04",
        time = "23:46",
        guests_num = 6,
    ),
)
