package com.yo.bronim.repository

import android.util.Log
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.yo.bronim.interfaces.RegistrationApi
import com.yo.bronim.models.User
import com.yo.bronim.models.UserRegistration
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class RegistrationPageRepository {

    private val auth = Firebase.auth

    private val client = OkHttpClient.Builder().build()
    private val retrofit = Retrofit.Builder()
        .client(client)
        .baseUrl("https://bmstusa.ru/")
        .addConverterFactory(MoshiConverterFactory.create().asLenient())
        .build()
    private val registrationApi = retrofit.create(RegistrationApi::class.java)

//    constructor() {
//        auth = FirebaseAuth.getInstance()
//    }

    suspend fun register(user: UserRegistration): UserRegistration {
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

    suspend fun postUserData(user: User) {
        Log.d(
            "GOT",
            "${registrationApi.sendUserData(user).body()}"
        )
    }
}
