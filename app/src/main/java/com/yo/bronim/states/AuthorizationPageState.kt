package com.yo.bronim.states

sealed class AuthorizationPageState {
    class Pending(): AuthorizationPageState()
    class Success(): AuthorizationPageState()
    class Error(error: Throwable): AuthorizationPageState()
}