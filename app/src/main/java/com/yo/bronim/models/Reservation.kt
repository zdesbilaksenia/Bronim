package com.yo.bronim.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ReservationListItem (
       @Json(name = "id") val id : Int?,
       @Json(name = "name") val name : String?,
       @Json(name = "address") val address : String?,
       @Json(name = "tags") val tags : List<String>?,
       @Json(name = "date") val date : String?,
       @Json(name = "time") val time : String?,
       @Json(name = "guests_num") val guests_num : Int?,
)

@JsonClass(generateAdapter = true)
data class Reservation (
       @Json(name = "id") val id : Int,
       @Json(name = "reservation_date") val date : String,
       @Json(name = "cells") val cells : Int,
       @Json(name = "num_of_guests") val guests_num : Int,
)

@JsonClass(generateAdapter = true)
data class RestaurantReservation(
       @Json(name = "restaurant") val restaurant: Restaurant,
       @Json(name = "reservation") val reservation: Reservation,
)


@JsonClass(generateAdapter = true)
data class RestaurantReservationList(
       @Json(name = "profile_reservations") val restaurantReservationList: Array<RestaurantReservation>
)

typealias ReservationsListCallback = (result: Array<ReservationListItem>?, error: Throwable?) -> Unit
