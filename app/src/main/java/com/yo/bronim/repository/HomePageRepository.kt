package com.yo.bronim.repository

import com.yo.bronim.models.Restaurant

class HomePageRepository {
    fun getPopularRestaurants(): Array<Restaurant> {
        return popularRests
    }

    fun getNearestRestaurants(): Array<Restaurant> {
        return popularRests
    }

    fun getNewRestaurants(): Array<Restaurant> {
        return newRests
    }

    companion object {
        private val tempCategory = arrayOf(
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

        private val popularRests = arrayOf(
            Restaurant("Ресторан1", "Адрес", arrayOf("Вино", "Пицца", "Да"), 4.2f),
            Restaurant("Ресторан2", "Адрес", arrayOf("Вино", "Пицца", "Длинный тег"), 4.1f),
            Restaurant("Ресторан3", "Адрес", arrayOf("Длинный тег", "Длинный тег", "Да"), 3.7f),
            Restaurant(
                "Ресторан4",
                "Адрес",
                arrayOf("Вино", "Длинный тег", "Да", "Длинный тег"),
                3.3f
            ),
            Restaurant("Ресторан5", "Адрес", arrayOf("Вино", "Пицца", "Да"), 5.0f),
            Restaurant("Ресторан6", "Адрес", arrayOf("Вино", "Пицца", "Да"), 2.1f),
        )

        private val newRests = arrayOf(
            Restaurant("Новый1", "Адрес", arrayOf(), 4.2f),
            Restaurant("Новый2", "Адрес", arrayOf(), 4.2f),
            Restaurant("Новый3", "Адрес", arrayOf(), 4.2f),
            Restaurant("Новый4", "Адрес", arrayOf(), 4.2f),
            Restaurant("Новый5", "Адрес", arrayOf(), 4.2f),
            Restaurant("Новый6", "Адрес", arrayOf(), 4.2f),
        )
    }
}
