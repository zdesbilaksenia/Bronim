package com.yo.bronim.repository

import com.yo.bronim.models.UserAuthorization
import kotlinx.coroutines.delay

class AuthorizationPageRepository {
    fun authorize(user: UserAuthorization) {
        if (user.email == null || user.password == null) {
            throw Exception("Not filled")
        }
    }
}