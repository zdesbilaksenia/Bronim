package com.yo.bronim.managers

import com.yo.bronim.models.User
import com.yo.bronim.providers.ProfilePageProvider

object ProfilePageManager {
    private val profileProvider = ProfilePageProvider()

    fun signOut(callback: (error: Throwable?) -> Unit) {
        profileProvider.signOut(callback)
    }

    fun saveProfile(callback: (user: User?, error: Throwable?) -> Unit, user: User?) {
        profileProvider.saveProfile(callback, user)
    }

    fun getProfile(callback: (user: User?, error: Throwable?) -> Unit) {
        profileProvider.getProfile(callback)
    }
}
