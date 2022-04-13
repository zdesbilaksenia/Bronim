package com.yo.bronim.models

data class UserAuthorization (
    val email: String,
    val password: String
) {

}

typealias AuthorizeCallback = (error: Throwable?) -> Unit