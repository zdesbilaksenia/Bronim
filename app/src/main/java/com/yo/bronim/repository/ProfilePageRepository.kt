package com.yo.bronim.repository

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.yo.bronim.interfaces.ProfileApi
import com.yo.bronim.models.User
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class ProfilePageRepository {
    private val auth = FirebaseAuth.getInstance()
    private val client = OkHttpClient.Builder().build()
    private val retrofit = Retrofit.Builder()
        .client(client)
        .baseUrl("https://bmstusa.ru/")
        .addConverterFactory(MoshiConverterFactory.create().asLenient())
        .build()

    private val profileApi = retrofit.create(ProfileApi::class.java)

    suspend fun signOut() {
        return suspendCoroutine { continuation ->
            if (auth.currentUser != null) {
                Log.i("isAuthorizedRepo", auth.currentUser!!.uid)
                auth.signOut()
                continuation.resume(Unit)
            } else {
                continuation.resumeWithException(Exception("not authorized"))
            }
        }
    }

    suspend fun getFirebaseUID(): String? {
        return suspendCoroutine { continuation ->
            if (auth.currentUser != null) {
                Log.i("isAuthorizedRepo", auth.currentUser!!.uid)
                continuation.resume(auth.currentUser!!.uid)
            } else {
                continuation.resumeWithException(Exception("not authorized"))
            }
        }
    }

    suspend fun saveProfile(fireBaseID: String?, user: User?): User? {
        return profileApi.updateProfile(fireBaseID, user)?.body()
    }

    suspend fun getProfile(fireBaseID: String?): User? {
        return profileApi.getProfile(fireBaseID)?.body()
    }
}
