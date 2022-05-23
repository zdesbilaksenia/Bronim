package com.yo.bronim.providers

import android.util.Log
import com.yo.bronim.models.User
import com.yo.bronim.repository.ProfilePageRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ProfilePageProvider {
    private val scope = CoroutineScope(Dispatchers.IO)
    private val profilePageRepository = ProfilePageRepository()

    private suspend fun invokeErrorCallback(
        callback: (error: Throwable?) -> Unit,
        error: Throwable?
    ) {
        withContext(Dispatchers.Main) {
            callback(error)
        }
    }

    private suspend fun invokeUserCallback(
        callback: (user: User?, error: Throwable?) -> Unit,
        user: User?,
        error: Throwable?
    ) {
        withContext(Dispatchers.Main) {
            callback(user, error)
        }
    }

    fun signOut(callback: (error: Throwable?) -> Unit) {
        scope.launch {
            try {
                profilePageRepository.signOut()
                invokeErrorCallback(callback, null)
            } catch (error: Throwable) {
                Log.i("Failed:", "Ploho")
                Log.e("Error:", error.toString())
                invokeErrorCallback(callback, error)
            }
        }
    }

    fun saveProfile(callback: (user: User?, error: Throwable?) -> Unit, user: User?) {
        scope.launch {
            try {
                val firebaseID = profilePageRepository.getFirebaseUID()
                val resultUser = profilePageRepository.saveProfile(firebaseID, user)
                invokeUserCallback(callback, resultUser, null)
            } catch (error: Throwable) {
                Log.i("Failed:", "Ploho")
                Log.e("Error:", error.toString())
                invokeUserCallback(callback, null, error)
            }
        }
    }

    fun getProfile(callback: (user: User?, error: Throwable?) -> Unit) {
        scope.launch {
            try {
                val firebaseID = profilePageRepository.getFirebaseUID()
                val resultUser = profilePageRepository.getProfile(firebaseID)
                invokeUserCallback(callback, resultUser, null)
            } catch (error: Throwable) {
                Log.i("Failed:", "Ploho")
                Log.e("Error:", error.toString())
                invokeUserCallback(callback, null, error)
            }
        }
    }
}
