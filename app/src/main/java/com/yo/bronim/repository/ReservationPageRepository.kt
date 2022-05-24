package com.yo.bronim.repository

import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentActivity
import com.yo.bronim.R
import com.yo.bronim.contracts.EXTRA_USER_REGISTRATION
import com.yo.bronim.interfaces.ReservationApi
import com.yo.bronim.models.PostReservation
import com.yo.bronim.models.Reservation
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class ReservationPageRepository(private val callback: (Int) -> Unit) {
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
        val code = reservationApi.sendReservationInfo(postReservation, restId, chosenTable)
            .code()
        this.callback(code)
    }
}
