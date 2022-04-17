package com.yo.bronim.providers

import com.yo.bronim.models.AuthorizeCallback
import com.yo.bronim.models.UserAuthorization
import com.yo.bronim.repository.AuthorizationPageRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AuthorizationPageProvider {
    private val scope = CoroutineScope(Dispatchers.IO)
    private val authorizationPageRepository = AuthorizationPageRepository()

    private suspend fun invokeCallback(
        callback: AuthorizeCallback,
        user: UserAuthorization?,
        error: Throwable?
    ) {
        withContext(Dispatchers.Main) {
            callback(user, error)
        }
    }

    fun authorize(callback: AuthorizeCallback, user: UserAuthorization) {
        scope.launch {
            try {
                val resultUser = authorizationPageRepository.authorize(user)
                invokeCallback(callback, resultUser, null)
            } catch (error: Throwable) {
                invokeCallback(callback, null, error)
            }
        }
    }
}
