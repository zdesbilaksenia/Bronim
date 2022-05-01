package com.yo.bronim.managers

import com.yo.bronim.models.AuthorizeCallback
import com.yo.bronim.models.UserAuthorization
import com.yo.bronim.providers.AuthorizationPageProvider
import com.yo.bronim.providers.ProfilePageProvider

object ProfilePageManager {
    private val profileProvider = ProfilePageProvider()

    fun signOut(callback: (error: Throwable?) -> Unit) {
        profileProvider.signOut(callback)
    }

    fun saveProfile(callback: (error: Throwable?) -> Unit) {
        profileProvider.signOut(callback)
    }
}