package com.yo.bronim.contracts

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.activity.result.contract.ActivityResultContract
import com.yo.bronim.ReservationActivity
import com.yo.bronim.models.Restaurant

class ReservationContract() :
    ActivityResultContract<Restaurant?, Int?>() {
    override fun createIntent(context: Context, input: Restaurant?): Intent {
        val intent = Intent(context, ReservationActivity::class.java)
        intent.putExtra("start", input?.start)
        intent.putExtra("end", input?.end)
        intent.putExtra("id", input?.id)
        Log.e("REST", "$input")
        return intent
    }

    override fun parseResult(resultCode: Int, intent: Intent?): Int? {
        Log.e("CONTRACT", "dome")
        if (intent === null) return null
        if (resultCode != Activity.RESULT_OK) return null

        return intent.getIntExtra(EXTRA_CODE_RESERVATION, 0)
    }
}
