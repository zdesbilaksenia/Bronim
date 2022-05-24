package com.yo.bronim

import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.yo.bronim.fragments.restaurant.RestaurantFragment


class RestaurantActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_restaurant)
        val intent = intent
        val restaurantID = intent.getIntExtra("restaurantID", 0)
        val restaurantFragment = RestaurantFragment.newInstance(restaurantID)
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.restaurant_fragment_container, restaurantFragment)
        transaction.commit()
    }

    fun showToast(code: Int?) {
        var layout: View? = null
        layout = if (code == 200)
            layoutInflater.inflate(R.layout.toast_ok, null)
        else
            layoutInflater.inflate(R.layout.toast_error, null)

        val toast = Toast(applicationContext)
        toast.setGravity(Gravity.FILL, 0, 0)
        toast.duration = Toast.LENGTH_SHORT
        toast.view = layout
        toast.show()
    }
}
