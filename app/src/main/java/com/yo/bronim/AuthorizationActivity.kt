package com.yo.bronim

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.yo.bronim.contracts.EXTRA_USER_AUTHORIZATION
import com.yo.bronim.models.User

class AuthorizationActivity : AppCompatActivity() {

    val auth = Firebase.auth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.signin_activity)
    }

    fun sendResultUser(user: User?) {
        val data = Intent().putExtra(EXTRA_USER_AUTHORIZATION, user)
        setResult(RESULT_OK, data)
        finish()
    }

    companion object {
        fun newInstance(context: Context?) = Intent(context, AuthorizationActivity::class.java)
        private val auth = Firebase.auth
        fun isAuthorized(): Boolean {
            val user = auth.currentUser
            return user != null
        }
    }
}
