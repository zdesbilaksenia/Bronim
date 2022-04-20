package com.yo.bronim.states

import com.yo.bronim.models.User

sealed class AuthorizationPageState {
    object Pending : AuthorizationPageState()
    class Success(val user: User?) : AuthorizationPageState()
    class Error(val error: Throwable?) : AuthorizationPageState()
}
