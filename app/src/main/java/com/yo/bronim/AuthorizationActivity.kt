package com.yo.bronim

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.yo.bronim.contracts.EXTRA_USER_AUTHORIZATION
import com.yo.bronim.models.UserAuthorization

class AuthorizationActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.signin_activity)
    }

    fun sendResultUser(user: UserAuthorization?) {
        val data = Intent().putExtra(EXTRA_USER_AUTHORIZATION, user)
        setResult(RESULT_OK, data)
        finish()
    }

    companion object {
        fun newInstance(context: Context?) = Intent(context, AuthorizationActivity::class.java)
    }
}
