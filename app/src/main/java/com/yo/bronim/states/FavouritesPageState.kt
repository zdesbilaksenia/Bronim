package com.yo.bronim.states

import com.yo.bronim.models.Restaurant

sealed class FavouritesPageState {
    class Pending : FavouritesPageState()
    class Success(val result: Array<Restaurant>) : FavouritesPageState()
    class Error(val error: Throwable) : FavouritesPageState()
}
