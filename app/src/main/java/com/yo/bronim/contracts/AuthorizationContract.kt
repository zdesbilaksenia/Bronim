package com.yo.bronim.contracts

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.activity.result.contract.ActivityResultContract
import com.yo.bronim.AuthorizationActivity
import com.yo.bronim.models.UserAuthorization

class AuthorizationContract : ActivityResultContract<Unit, String?>() {
    override fun createIntent(context: Context, input: Unit?): Intent {
        return Intent(context, AuthorizationActivity::class.java)
    }

    override fun parseResult(resultCode: Int, intent: Intent?): String? {
        if (intent === null) return null
        if (resultCode != Activity.RESULT_OK) return null

        val user = intent.getParcelableExtra<UserAuthorization>(EXTRA_USER_AUTHORIZATION)
        return user?.email
    }
}
