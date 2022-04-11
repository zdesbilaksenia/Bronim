package com.yo.bronim.viewmodels

import androidx.lifecycle.MutableLiveData
import com.yo.bronim.models.UserAuthorization
import com.yo.bronim.managers.AuthorizationPageManager
import com.yo.bronim.states.AuthorizationPageState

class AuthorizationPageViewModel {
    private val authorizationPageManager = AuthorizationPageManager()
    val authorizationPageState = MutableLiveData<AuthorizationPageState>()

    fun authorize(user: UserAuthorization) {
        authorizationPageState.postValue(AuthorizationPageState.Pending())
        authorizationPageManager.authorize({ error ->
            when {
                error === null -> {
                    authorizationPageState.postValue(AuthorizationPageState.Success())
                }
                else -> {
                    authorizationPageState.postValue(AuthorizationPageState.Error(error))
                }
            }
        }, user)
    }
}