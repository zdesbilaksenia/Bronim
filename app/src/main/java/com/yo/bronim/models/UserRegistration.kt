package com.yo.bronim.models

import android.os.Parcel
import android.os.Parcelable

data class UserRegistration(
    var uid: String? = null,
    val name: String? = null,
    val email: String? = null,
    val password: String? = null,
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

    companion object CREATOR : Parcelable.Creator<UserRegistration> {
        override fun createFromParcel(parcel: Parcel): UserRegistration {
            return UserRegistration(parcel)
        }

        override fun newArray(size: Int): Array<UserRegistration?> {
            return arrayOfNulls(size)
        }
    }
}

typealias RegisterCallback = (user: User?, error: Throwable?) -> Unit
