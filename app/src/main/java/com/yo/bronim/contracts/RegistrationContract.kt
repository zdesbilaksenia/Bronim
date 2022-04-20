package com.yo.bronim.contracts

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.activity.result.contract.ActivityResultContract
import com.yo.bronim.RegistrationActivity
import com.yo.bronim.models.User

// TODO Out type and pasrsing
class RegistrationContract : ActivityResultContract<Unit, User?>() {
    override fun createIntent(context: Context, input: Unit?): Intent {
        return Intent(context, RegistrationActivity::class.java)
    }

    override fun parseResult(resultCode: Int, intent: Intent?): User? {
        if (intent === null) return null
        if (resultCode != Activity.RESULT_OK) return null

        return intent.getParcelableExtra<User>(EXTRA_USER_REGISTRATION)
    }
}
