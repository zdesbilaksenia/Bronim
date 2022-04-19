package com.yo.bronim.managers

import com.yo.bronim.models.AuthorizeCallback
import com.yo.bronim.models.UserAuthorization
import com.yo.bronim.providers.AuthorizationPageProvider

object AuthorizationManager {
    private val authorizationProvider = AuthorizationPageProvider()

    fun authorize(callback: AuthorizeCallback, user: UserAuthorization) {
        authorizationProvider.authorize(callback, user)
    }
}
