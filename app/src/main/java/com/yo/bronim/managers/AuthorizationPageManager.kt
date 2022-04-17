package com.yo.bronim.managers

import com.yo.bronim.models.AuthorizeCallback
import com.yo.bronim.models.UserAuthorization
import com.yo.bronim.providers.AuthorizationPageProvider

class AuthorizationPageManager {
    private val authorizationPageProvider = AuthorizationPageProvider()

    fun authorize(callback: AuthorizeCallback, user: UserAuthorization) {
        authorizationPageProvider.authorize(callback, user)
    }
}
