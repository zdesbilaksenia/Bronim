package com.yo.bronim.states

import com.yo.bronim.models.UserRegistration

sealed class RegistrationPageState {
    object Pending : RegistrationPageState()
    class Success(val user: UserRegistration) : RegistrationPageState()
    class Error(val error: Throwable) : RegistrationPageState()
}
