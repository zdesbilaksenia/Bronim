package com.yo.bronim.models

import android.os.Parcel
import android.os.Parcelable

data class UserAuthorization(
    var uid: String?,
    val name: String?,
    val email: String?,
    val password: String?
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
