package com.yo.bronim.models

data class UserRegistration (
        val name: String? = null,
        val email: String? = null,
        val password: String? = null,
)

typealias RegisterCallback = (error: Throwable?) -> Unit
