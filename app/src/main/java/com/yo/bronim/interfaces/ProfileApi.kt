package com.yo.bronim.interfaces

import com.yo.bronim.models.User
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Path

interface ProfileApi {
    @Headers("Content-Type: application/json")
    @POST("bronim/profiles/{id}")
    suspend fun updateProfile(
        @Path("id") firebaseID: String?,
        @Body user: User?
    ): Response<User?>?

    @Headers("Content-Type: application/json")
    @GET("bronim/profiles/{id}")
    suspend fun getProfile(
        @Path("id") firebaseID: String?
    ): Response<User?>?
}
