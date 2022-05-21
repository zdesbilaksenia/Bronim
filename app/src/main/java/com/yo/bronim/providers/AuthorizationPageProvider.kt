package com.yo.bronim.providers

import android.util.Log
import com.yo.bronim.models.AuthorizeCallback
import com.yo.bronim.models.User
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
        user: User?,
        error: Throwable?
    ) {
        withContext(Dispatchers.Main) {
            callback(user, error)
        }
    }

    private suspend fun invokeErrorCallback(
        callback: (error: Throwable?) -> Unit,
        error: Throwable?
    ) {
        withContext(Dispatchers.Main) {
            callback(error)
        }
    }

    fun authorize(callback: AuthorizeCallback, user: UserAuthorization) {
        scope.launch {
            try {
                val fbUser = authorizationPageRepository.authorize(user)
                val result = authorizationPageRepository.getUserData(fbUser.uid)
                Log.i("Success:", result?.name!!)
                val resultUser = User(
                    uid = result.uid,
                    name = result.name,
                    email = result.email,
                )
                invokeCallback(callback, resultUser, null)
            } catch (error: Throwable) {
                Log.i("Failed:", "Ploho")
                Log.e("Error:", error.toString())
                invokeCallback(callback, null, error)
            }
        }
    }

    fun isAuthorized(callback: (error: Throwable?) -> Unit) {
        scope.launch {
            try {
                authorizationPageRepository.isAuthorized()
                Log.i("Success:", "Horosho")
                invokeErrorCallback(callback, null)
            } catch (error: Throwable) {
                Log.i("Failed:", "Ploho")
                invokeErrorCallback(callback, error)
            }
        }
    }
}
