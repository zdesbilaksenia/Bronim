package com.yo.bronim.contracts

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.activity.result.contract.ActivityResultContract
import com.yo.bronim.AuthorizationActivity
import com.yo.bronim.models.User

class AuthorizationContract : ActivityResultContract<Unit, User?>() {
    override fun createIntent(context: Context, input: Unit?): Intent {
        return Intent(context, AuthorizationActivity::class.java)
    }

    override fun parseResult(resultCode: Int, intent: Intent?): User? {
        if (intent === null) return null
        if (resultCode != Activity.RESULT_OK) return null

        return intent.getParcelableExtra(EXTRA_USER_AUTHORIZATION)
    }
}
