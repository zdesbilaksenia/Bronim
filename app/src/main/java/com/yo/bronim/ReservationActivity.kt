package com.yo.bronim

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity

class ReservationActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("Started", "act")
        setContentView(R.layout.activity_reservation);
    }
}