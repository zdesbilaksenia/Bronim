package com.yo.bronim.providers

import android.util.Log
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

    fun signOut(callback: (error: Throwable?) -> Unit) {
        scope.launch {
            try {
                profilePageRepository.signOut()
                invokeErrorCallback(callback,null)
            } catch (error: Throwable) {
                Log.i("Failed:", "Ploho")
                Log.e("Error:", error.toString())
                invokeErrorCallback(callback,error)
            }
        }
    }

    fun saveProfile(callback: (error: Throwable?) -> Unit) {
        scope.launch {
            try {
                profilePageRepository.saveProfile()
                invokeErrorCallback(callback,null)
            } catch (error: Throwable) {
                Log.i("Failed:", "Ploho")
                Log.e("Error:", error.toString())
                invokeErrorCallback(callback,error)
            }
        }
    }
}