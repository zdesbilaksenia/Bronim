package com.yo.bronim.states

sealed class SubscribeState {
    class Pending : SubscribeState()
    class Success : SubscribeState()
    class Error(val error: Throwable?) : SubscribeState()
}
