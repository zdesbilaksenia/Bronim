package com.yo.bronim

import com.yo.bronim.R
import android.content.Context
import android.os.Bundle
import android.util.AttributeSet
import android.util.Log
import android.view.View
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
}
