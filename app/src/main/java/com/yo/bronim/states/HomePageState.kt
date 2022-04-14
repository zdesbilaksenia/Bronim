package com.yo.bronim.states

import com.yo.bronim.models.Restaurant

sealed class HomePageState {
    class Pending : HomePageState()
    class Success(val result: Array<Restaurant>) : HomePageState()
    class Error(val error: Throwable) : HomePageState()
}
