package com.yo.bronim.viewmodels

import androidx.lifecycle.MutableLiveData
import com.yo.bronim.managers.RegistrationPageManager
import com.yo.bronim.models.UserRegistration
import com.yo.bronim.states.RegistrationPageState

class RegistrationPageViewModel {
    private val registrationPageManager = RegistrationPageManager()
    val registrationState = MutableLiveData<RegistrationPageState>()

    fun register(user: UserRegistration) {
        registrationState.postValue(RegistrationPageState.Pending())
        registrationPageManager.register({ result, error ->
            when {
                result != null -> {
                    registrationState.postValue(RegistrationPageState.Success(result))
                }
                else -> {
                    registrationState.postValue(RegistrationPageState.Error(error!!))
                }
            }
        }, user)
    }
}
