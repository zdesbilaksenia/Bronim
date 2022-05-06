package com.yo.bronim.repository

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.yo.bronim.interfaces.AuthorizationApi
import com.yo.bronim.models.UserAuthorization
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class AuthorizationPageRepository {
    private val auth = FirebaseAuth.getInstance()
    private val client = OkHttpClient.Builder().build()
    private val retrofit = Retrofit.Builder()
        .client(client)
        .baseUrl("https://bmstusa.ru/")
        .addConverterFactory(MoshiConverterFactory.create().asLenient())
        .build()

    private val authorizationApi = retrofit.create(AuthorizationApi::class.java)

    suspend fun authorize(user: UserAuthorization): UserAuthorization {
        if (auth.currentUser != null) {
            Log.i("UID:", auth.currentUser!!.uid)
        }
        return suspendCoroutine { continuation ->
            auth.signInWithEmailAndPassword(user.email!!, user.password!!)
                .addOnCompleteListener {
                    if (it.isSuccessful) {
                        user.uid = auth.currentUser?.uid
                        continuation.resume(user)
                    } else {
                        continuation.resumeWithException(it.exception!!)
                    }
                }
        }
    }

    suspend fun getUserData(firebaseID: String?): UserAuthorization? {
        return authorizationApi.authorize(firebaseID)?.body()
    }

    /*

    suspend fun getUId(): String? {
        if (auth.currentUser != null) {
            return auth.currentUser?.uid
        } else {
            throw Exception("Auth.currentUser is null")
        }
    }
     */
}
