package com.yo.bronim.models

import android.os.Parcel
import android.os.Parcelable
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
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.createStringArrayList(),
        parcel.readFloat(),
        parcel.readInt(),
        parcel.readInt()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(name)
        parcel.writeString(address)
        parcel.writeString(description)
        parcel.writeString(img)
        parcel.writeString(phone)
        parcel.writeString(email)
        parcel.writeString(website)
        parcel.writeStringList(tags)
        parcel.writeFloat(rating)
        parcel.writeInt(start)
        parcel.writeInt(end)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Restaurant> {
        override fun createFromParcel(parcel: Parcel): Restaurant {
            return Restaurant(parcel)
        }

        override fun newArray(size: Int): Array<Restaurant?> {
            return arrayOfNulls(size)
        }
    }
}

@JsonClass(generateAdapter = true)
data class RestaurantList(
    @Json(name = "restaurants") val restaurants: Array<Restaurant>,
)
