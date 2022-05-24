package com.yo.bronim

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.yo.bronim.contracts.EXTRA_CODE_RESERVATION
import com.yo.bronim.fragments.reservationfragment.ReservationFragment

class ReservationActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reservation)
        val intent = intent

        val start = intent.getIntExtra("start", 0)
        val end = intent.getIntExtra("end", 0)
        val id = intent.getIntExtra("id", 0)

        val reservationFragment = ReservationFragment.newInstance(start, end, id)
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.reservation_fragment_container, reservationFragment)
        transaction.commit()
    }

    fun sendCode(code: Int) {
        val data = Intent().putExtra(EXTRA_CODE_RESERVATION, code)
        setResult(AppCompatActivity.RESULT_OK, data)
        finish()
    }
}
