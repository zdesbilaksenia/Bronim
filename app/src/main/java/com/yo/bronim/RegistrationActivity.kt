package com.yo.bronim

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.yo.bronim.contracts.EXTRA_USER_REGISTRATION
import com.yo.bronim.models.UserRegistration

class RegistrationActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)
        auth = Firebase.auth
    }

    override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
        if (currentUser != null) {
//            TODO
        }
    }

    fun sendResult(user: UserRegistration) {
        val data = Intent().putExtra(EXTRA_USER_REGISTRATION, user)
        setResult(RESULT_OK, data)
        finish()
    }

    companion object {
        fun newInstance(context: Context?) = Intent(context, RegistrationActivity::class.java)
    }
}
