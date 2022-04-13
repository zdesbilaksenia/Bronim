package com.yo.bronim.providers

import com.yo.bronim.models.RegisterCallback
import com.yo.bronim.models.UserRegistration
import com.yo.bronim.repository.RegistrationPageRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class RegistrationPageProvider {
    private val scope = CoroutineScope(Dispatchers.IO)
    private val registrationPageRepository = RegistrationPageRepository()

    private suspend fun invokeCallback(
        callback: RegisterCallback,
        user: UserRegistration?,
        error: Throwable?
    ) {
        withContext(Dispatchers.Main) {
            callback(user, error)
        }
    }

    fun register(callback: RegisterCallback, user: UserRegistration) {
        scope.launch {
            try {
                val result = registrationPageRepository.register(user)
                invokeCallback(callback, result, null)
            } catch (error: Throwable) {
                invokeCallback(callback, null, error)
            }
        }
    }
}
