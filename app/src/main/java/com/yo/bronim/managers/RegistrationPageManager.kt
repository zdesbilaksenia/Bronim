package com.yo.bronim.managers

import com.yo.bronim.models.RegisterCallback
import com.yo.bronim.models.UserRegistration
import com.yo.bronim.providers.RegistrationPageProvider

class RegistrationPageManager {
    private val registrationPageProvider = RegistrationPageProvider()

    fun register(callback: RegisterCallback, user: UserRegistration) {
        registrationPageProvider.register(
            { result, error ->
                callback(result, error)
            },
            user
        )
    }
}
