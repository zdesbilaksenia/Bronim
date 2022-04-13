package com.yo.bronim.models

data class UserRegistration(
    var uid: String? = null,
    val name: String? = null,
    val email: String? = null,
    val password: String? = null,
)

typealias RegisterCallback = (user: UserRegistration?, error: Throwable?) -> Unit
