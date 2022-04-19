package com.yo.bronim.interfaces

import com.yo.bronim.models.UserAuthorization
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path


interface AuthorizationApi {
    @Headers("Content-Type: application/json")
    @GET("bronim/profiles/{id}")
    suspend fun authorize(
        @Path("id") Uid : String?
    ): Response<UserAuthorization?>?

}