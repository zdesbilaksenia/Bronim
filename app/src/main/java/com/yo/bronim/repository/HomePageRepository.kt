package com.yo.bronim.repository

import android.os.SystemClock
import com.yo.bronim.models.Restaurant


class MainPageRepository {
    fun getRecommendedRestaurants(): Array<Restaurant> {
//        SystemClock.sleep(4000)
        return popularRests
    }

    fun getNearestRestaurants(): Array<Restaurant> {
        return newRests
    }

    fun getNewRestaurants(): Array<Restaurant> {
//        SystemClock.sleep(5000)
        return newRests
    }

    fun getKitchens(): Array<String> {
        return kitchens
    }
}

val tempCategory = arrayOf(
    arrayOf(
        "0",
        "Популярное"
    ),
    arrayOf(
        "1",
        "Кухни"
    ),
    arrayOf(
        "0",
        "Близко к Вам"
    ),
    arrayOf(
        "0",
        "Популярное"
    ),
)

val kitchens = arrayOf(
    "японская",
    "русская",
    "итальянская",
    "китайская",
    "грузинская",
    "французская",
)

val popularRests = arrayOf(
    Restaurant("Ресторан1", "Адрес"),
    Restaurant("Ресторан2", "Адрес"),
    Restaurant("Ресторан3", "Адрес"),
    Restaurant("Ресторан4", "Адрес"),
    Restaurant("Ресторан5", "Адрес"),
    Restaurant("Ресторан6", "Адрес"),
)

val newRests = arrayOf(
    Restaurant("Новый1", "Адрес"),
    Restaurant("Новый2", "Адрес"),
    Restaurant("Новый3", "Адрес"),
    Restaurant("Новый4", "Адрес"),
    Restaurant("Новый5", "Адрес"),
    Restaurant("Новый6", "Адрес"),
)