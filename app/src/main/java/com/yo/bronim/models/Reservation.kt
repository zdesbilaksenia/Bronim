package com.yo.bronim.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Reservation (
       @Json(name = "id") val id : Int,
       @Json(name = "name") val name : String,
       @Json(name = "address") val address : String,
       @Json(name = "tags") val tags : List<String>,
       @Json(name = "date") val date : String,
       @Json(name = "time") val time : String,
       @Json(name = "guests_num") val guests_num : Int,
)

@JsonClass(generateAdapter = true)
data class RestaurantReservation(
       @Json(name = "restaurant") val restaurant: Restaurant,
       @Json(name = "reservation") val reservation: Reservation,
)


@JsonClass(generateAdapter = true)
data class ReservationRestaurantList(
       @Json(name = "profile_reservations") val restaurantReservationList: Array<RestaurantReservation>
)

typealias ReservationsListCallback = (result: Array<Reservation>?, error: Throwable?) -> Unit
