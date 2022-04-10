package com.yo.bronim.states

sealed class RegistrationPageState {
    class Pending(): RegistrationPageState()
    class Success(): RegistrationPageState()
    class Error(error: Throwable): RegistrationPageState()
}
