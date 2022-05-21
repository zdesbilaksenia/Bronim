package com.yo.bronim.viewmodels

import androidx.lifecycle.MutableLiveData
import com.yo.bronim.managers.ProfilePageManager
import com.yo.bronim.models.User
import com.yo.bronim.states.ProfilePageState

class ProfilePageViewModel {
    private val profilePageManager = ProfilePageManager
    val signOutState = MutableLiveData<ProfilePageState>()
    val saveProfileState = MutableLiveData<ProfilePageState>()
    val getProfileState = MutableLiveData<ProfilePageState>()

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

    fun saveProfile(user: User?) {
        saveProfileState.postValue(ProfilePageState.Pending)
        profilePageManager.saveProfile(
            { resultUser, error ->
                when {
                    error == null -> {
                        saveProfileState.postValue(ProfilePageState.Success(resultUser))
                    }
                    else -> {
                        saveProfileState.postValue(ProfilePageState.Error(error))
                    }
                }
            },
            user
        )
    }

    fun getProfile() {
        getProfileState.postValue(ProfilePageState.Pending)
        profilePageManager.getProfile { resultUser, error ->
            when {
                error == null -> {
                    getProfileState.postValue(ProfilePageState.Success(resultUser))
                }
                else -> {
                    getProfileState.postValue(ProfilePageState.Error(error))
                }
            }
        }
    }
}
