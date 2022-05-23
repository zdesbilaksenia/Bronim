package com.yo.bronim.viewmodels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.yo.bronim.managers.AuthorizationManager
import com.yo.bronim.models.UserAuthorization
import com.yo.bronim.states.AuthorizationPageState

class AuthorizationPageViewModel {
    private val authorizationPageManager = AuthorizationManager
    val authorizationPageState = MutableLiveData<AuthorizationPageState>()
    val isAuthorizedState = MutableLiveData<AuthorizationPageState>()

    fun authorize(user: UserAuthorization) {
        authorizationPageState.postValue(AuthorizationPageState.Pending)
        authorizationPageManager.authorize(
            { resultUser, error ->
                when {
                    resultUser != null -> {
                        authorizationPageState.postValue(AuthorizationPageState.Success(resultUser))
                    }
                    else -> {
                        authorizationPageState.postValue(AuthorizationPageState.Error(error))
                    }
                }
            },
            user
        )
    }

    fun isAuthorized() {
        isAuthorizedState.postValue(AuthorizationPageState.Pending)
        authorizationPageManager.isAuthorized { error ->
            when {
                error == null -> {
                    Log.i("APVM", "Success")
                    isAuthorizedState.postValue(AuthorizationPageState.Success(null))
                }
                else -> {
                    Log.i("APVM", "Error")
                    Log.e("APVM", error.toString())
                    isAuthorizedState.postValue(AuthorizationPageState.Error(error))
                }
            }
        }
    }
}
