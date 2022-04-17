package com.yo.bronim.models

import com.squareup.moshi.Json

data class Restaurant(
    @Json(name = "id") val id: Int,
    @Json(name = "name") val name: String?,
    @Json(name = "address") val address: String,
    @Json(name = "description") val description: String,
    @Json(name = "img_url") val img: String,
    @Json(name = "phone_number") val phone: String,
    @Json(name = "email") val email: String,
    @Json(name = "website_url") val website: String,
//    @Json(name = "geoposition") val geoposition: String,
    @Json(name = "tags") val tags: List<String>,
    @Json(name = "rating") val rating: Float,
)
