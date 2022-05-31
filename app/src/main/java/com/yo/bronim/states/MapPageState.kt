package com.yo.bronim.states

import com.yo.bronim.models.Restaurant

sealed class MapPageState {
    class Pending : MapPageState()
    class Success(val result: Array<Restaurant>) : MapPageState()
    class Error(val error: Throwable) : MapPageState()
}
