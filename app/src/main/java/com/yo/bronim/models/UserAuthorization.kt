package com.yo.bronim.models

import android.os.Parcel
import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class UserAuthorization(
    @Json(name = "firebase_id") var uid: String?,
    @Json(name = "name") val name: String?,
    @Json(name = "email") val email: String?,
    @Json(name = "password") val password: String?
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(uid)
        parcel.writeString(name)
        parcel.writeString(email)
        parcel.writeString(password)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<UserAuthorization> {
        override fun createFromParcel(parcel: Parcel): UserAuthorization {
            return UserAuthorization(parcel)
        }

        override fun newArray(size: Int): Array<UserAuthorization?> {
            return arrayOfNulls(size)
        }
    }
}

typealias AuthorizeCallback = (user: UserAuthorization?, error: Throwable?) -> Unit
