package com.yo.bronim.states

import com.yo.bronim.models.User

sealed class ProfilePageState {
    object Pending : ProfilePageState()
    class Success(val user: User?) : ProfilePageState()
    class Error(val error: Throwable?) : ProfilePageState()
}