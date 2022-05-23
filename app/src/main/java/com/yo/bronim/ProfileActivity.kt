package com.yo.bronim

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.yo.bronim.contracts.EXTRA_USER_REGISTRATION
import com.yo.bronim.models.User

class ProfileActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
    }

    fun sendResult(user: User?) {
        val data = Intent().putExtra(EXTRA_USER_REGISTRATION, user)
        setResult(RESULT_OK, data)
        finish()
    }

    companion object {
        fun newInstance(context: Context?) = Intent(context, ProfileActivity::class.java)
    }
}
