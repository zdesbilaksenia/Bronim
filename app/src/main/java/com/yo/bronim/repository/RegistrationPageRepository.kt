package com.yo.bronim.repository

import android.os.SystemClock
import android.util.Log
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.yo.bronim.models.UserRegistration
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class RegistrationPageRepository {

    private val auth = Firebase.auth

//    constructor() {
//        auth = FirebaseAuth.getInstance()
//    }

    suspend fun register(user: UserRegistration): UserRegistration {
        auth.currentUser
        return suspendCoroutine { continuation ->
            auth.createUserWithEmailAndPassword(user.email!!, user.password!!)
                .addOnCompleteListener { task ->
                    Log.i("DEBUG", "in complete")
                    if (task.isSuccessful) {
                        user.uid = auth.currentUser?.uid
                        continuation.resume(user)
                    } else {
                        continuation.resumeWithException(task.exception!!)
                    }
                }

        }
    }
}
