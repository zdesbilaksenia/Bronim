package com.yo.bronim.registrationfragment;


import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.firebase.auth.*
import com.yo.bronim.MainActivity
import com.yo.bronim.R
import com.yo.bronim.models.UserRegistration
import com.yo.bronim.states.RegistrationPageState
import com.yo.bronim.states.RegistrationPageState.*
import com.yo.bronim.viewmodels.RegistrationPageViewModel

class RegistrationFragment : Fragment() {

    private val registrationPageViewModel = RegistrationPageViewModel()

    private val editTextName by lazy {
        view?.findViewById<EditText>(R.id.registration_page__name_edit_text)
    }
    private val editTextEmail by lazy {
        view?.findViewById<EditText>(R.id.registration_page__email_edit_text)
    }
    private val editTextPassword by lazy {
        view?.findViewById<EditText>(R.id.registration_page__password_edit_text)
    }
    private val editTextPasswordRepeated by lazy {
        view?.findViewById<EditText>(R.id.registration_page__password_again_edit_text)
    }
    private val buttonRegister by lazy {
        view?.findViewById<Button>(R.id.registration_page__create_account_button)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_registration_page, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        buttonRegister?.setOnClickListener{
            register()
        }

        registrationPageViewModel.registrationState.observe(viewLifecycleOwner) { state ->
            when(state) {
                is Pending -> showLoader(true)
                is Success -> {
                    showLoader(false)
                    val intent = Intent(activity, MainActivity::class.java)
                    startActivity(intent)
                }
                is Error -> {
                    showLoader(false)
                    val text = when (state.error) {
                        is FirebaseAuthWeakPasswordException -> getString(R.string.errorWeakPassword)
                        is FirebaseAuthUserCollisionException -> getString(R.string.errorUserExists)
                        else -> "Try again later"
                    }
                    Toast.makeText(
                        activity,
                        "Failed to create an account! $text",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }
    }

    private fun register() {
        val name = editTextName?.text.toString().trim()
        val email = editTextEmail?.text.toString().trim()
        val password = editTextPassword?.text.toString().trim()
        val passwordRepeated = editTextPasswordRepeated?.text.toString().trim()

        if (name.isEmpty()) {
            editTextName?.error = "Name is required!"
            editTextName?.requestFocus()
            return
        }

        if (email.isEmpty()) {
            editTextEmail?.error = "Email is required!"
            editTextEmail?.requestFocus()
            return
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
             editTextEmail?.error = "Please enter valid email!"
             editTextEmail?.requestFocus()
             return
        }

        if (password.isEmpty()) {
            editTextPassword?.error = "Password is required!"
            editTextPassword?.requestFocus()
            return
        }
        if (password.length < 6) {
            editTextPassword?.error = "Min password length is 6 chars!"
            editTextPassword?.requestFocus()
            return
        }

        if (password != passwordRepeated) {
            editTextPassword?.error = "Passwords should match!"
            editTextPasswordRepeated?.error = "Passwords should match!"
            editTextPassword?.requestFocus()
            return
        }

        val user = UserRegistration(null, name, email, password)
        registrationPageViewModel.register(user)
    }

    private fun showLoader(show: Boolean) {
//        TODO
    }
}
