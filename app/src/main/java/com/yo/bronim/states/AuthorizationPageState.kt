package com.yo.bronim.states

import com.yo.bronim.models.UserAuthorization

sealed class AuthorizationPageState {
    object Pending : AuthorizationPageState()
    class Success(val user: UserAuthorization?) : AuthorizationPageState()
    class Error(val error: Throwable?) : AuthorizationPageState()
}
