package com.yo.bronim.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Reservation(
    @Json(name = "table_id") val table: Int,
    @Json(name = "reserved_cells") val times: List<Int>,
)

@JsonClass(generateAdapter = true)
data class ReservationsList(
    @Json(name = "reservations") val reservations: Array<Reservation>?,
)

@JsonClass(generateAdapter = true)
data class PostReservation(
    @Json(name = "table_id") val tableId: String,
    @Json(name = "profile_id") val profileId: String,
    @Json(name = "reservation_date") val date: String,
    @Json(name = "num_of_guests") val guests: Int?,
    @Json(name = "cells") val cells: MutableList<Int>,
    @Json(name = "comment") val comment: String?
)
