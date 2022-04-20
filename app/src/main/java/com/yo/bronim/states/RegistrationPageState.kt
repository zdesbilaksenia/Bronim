package com.yo.bronim.states

import com.yo.bronim.models.User

sealed class RegistrationPageState {
    object Pending : RegistrationPageState()
    class Success(val user: User) : RegistrationPageState()
    class Error(val error: Throwable) : RegistrationPageState()
}
