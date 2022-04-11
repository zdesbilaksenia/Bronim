package com.yo.bronim.models

data class UserAuthorization (
    val email: String? = null,
    val password: String? = null,
)

typealias AuthorizeCallback = (error: Throwable?) -> Unit