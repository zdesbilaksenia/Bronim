package com.yo.bronim.states

import com.yo.bronim.models.Restaurant

sealed class CuisineFiltrationState {
    object Pending : CuisineFiltrationState()
    class Success(val result: Array<Restaurant>) : CuisineFiltrationState()
    class Error(val error: Throwable) : CuisineFiltrationState()
}
