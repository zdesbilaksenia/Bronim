package com.yo.bronim.interfaces

import com.yo.bronim.models.User
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface RegistrationApi {
    @Headers("Content-Type: application/json")
    @POST("/bronim/profiles")
    suspend fun sendUserData(
        @Body userData: User,
    ): Response<User>
}
