package com.yo.bronim.models

data class Restaurant(
    val name: String? = null,
    val address: String? = null,
    val tags: Array<String>? = null,
    val rating: Float,
)
