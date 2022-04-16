package com.yo.bronim.repository

import android.os.Debug
import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.yo.bronim.models.UserAuthorization
import kotlinx.coroutines.withContext
import kotlin.coroutines.coroutineContext
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine


class AuthorizationPageRepository {
    private val auth = FirebaseAuth.getInstance()
    suspend fun authorize(user: UserAuthorization): UserAuthorization {
        return suspendCoroutine { continuation ->
            auth.signInWithEmailAndPassword(user.email!!,user.password!!)
                .addOnCompleteListener{
                    if (it.isSuccessful) {
                        user.uid = auth.currentUser?.uid
                        continuation.resume(user)
                    } else {
                        continuation.resumeWithException(it.exception!!)
                    }
            }
        }
    }
}
