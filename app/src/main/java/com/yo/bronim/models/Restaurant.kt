package com.yo.bronim.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
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
    @Json(name = "tags") val tags: List<String>?,
    @Json(name = "rating") val rating: Float,
    @Json(name = "starts_at_cell_id") val start: Int,
    @Json(name = "ends_at_cell_id") val end: Int,
)

@JsonClass(generateAdapter = true)
data class RestaurantList(
    @Json(name = "restaurants") val restaurants: Array<Restaurant>,
)
