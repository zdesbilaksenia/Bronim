package com.yo.bronim.viewmodels

import androidx.lifecycle.MutableLiveData
import com.yo.bronim.managers.ProfilePageManager
import com.yo.bronim.states.ProfilePageState

class ProfilePageViewModel {
    private val profilePageManager = ProfilePageManager
    val signOutState = MutableLiveData<ProfilePageState>()
    val saveProfileState = MutableLiveData<ProfilePageState>()

    fun signOut() {
        signOutState.postValue(ProfilePageState.Pending)
        profilePageManager.signOut { error ->
            when {
                error == null -> {
                    signOutState.postValue(ProfilePageState.Success(null))
                }
                else -> {
                    signOutState.postValue(ProfilePageState.Error(error))
                }
            }

        }
    }

    fun saveProfile() {
        saveProfileState.postValue(ProfilePageState.Pending)
        profilePageManager.saveProfile { error ->
            when {
                error == null -> {
                    saveProfileState.postValue(ProfilePageState.Success(null))
                }
                else -> {
                    saveProfileState.postValue(ProfilePageState.Error(error))
                }
            }

        }
    }
}