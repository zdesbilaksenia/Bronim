package com.yo.bronim

import android.os.Bundle
import android.os.PersistableBundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.yo.bronim.models.UserAuthorization
import com.yo.bronim.viewmodels.AuthorizationPageViewModel

class SignInActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.signin_activity)
    }

}