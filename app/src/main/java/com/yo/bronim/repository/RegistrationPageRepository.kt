package com.yo.bronim.repository

import android.os.SystemClock
import com.yo.bronim.models.UserRegistration

class RegistrationPageRepository {
    fun register(user: UserRegistration) {
        SystemClock.sleep(1000)
        if (user.name == null || user.email === null
            || user.password === null || user.passwordRepeated === null) {
            throw Exception("Fill all fields of registration form!")
        }
    }
}
