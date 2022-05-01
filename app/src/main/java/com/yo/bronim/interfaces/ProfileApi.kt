package com.yo.bronim.interfaces

import com.yo.bronim.models.UserAuthorization
import retrofit2.Response
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Path

interface ProfileApi {
    @Headers("Content-Type: application/json")
    @POST("bronim/profiles/{id}")
    suspend fun updateProfile(
        @Path("id") firebaseID: String?
    ): Response<UserAuthorization?>?
}