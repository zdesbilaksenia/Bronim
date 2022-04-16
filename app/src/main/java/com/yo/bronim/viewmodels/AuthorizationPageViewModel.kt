package com.yo.bronim.viewmodels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.yo.bronim.models.UserAuthorization
import com.yo.bronim.managers.AuthorizationPageManager
import com.yo.bronim.states.AuthorizationPageState

class AuthorizationPageViewModel {
    private val authorizationPageManager = AuthorizationPageManager()
    val authorizationPageState = MutableLiveData<AuthorizationPageState>()

    fun authorize(user: UserAuthorization) {
        authorizationPageState.postValue(AuthorizationPageState.Pending)
        authorizationPageManager.authorize(
            {resultUser, error ->
                when {
                    resultUser != null -> {
                        authorizationPageState.postValue(AuthorizationPageState.Success(resultUser))
                    }
                    else -> {
                        authorizationPageState.postValue(AuthorizationPageState.Error(error))
                    }
                }
        }, user)
    }
}
