package com.yo.bronim.models

data class UserAuthorization (
    var uid: String?,
    val name: String?,
    val email: String,
    val password: String
)

typealias AuthorizeCallback = (user: UserAuthorization?, error: Throwable?) -> Unit