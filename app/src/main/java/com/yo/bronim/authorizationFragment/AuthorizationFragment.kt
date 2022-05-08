package com.yo.bronim.authorizationFragment

import android.os.Bundle
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.yo.bronim.AuthorizationActivity
import com.yo.bronim.R
import com.yo.bronim.contracts.RegistrationContract
import com.yo.bronim.models.UserAuthorization
import com.yo.bronim.states.AuthorizationPageState
import com.yo.bronim.viewmodels.AuthorizationPageViewModel

class AuthorizationFragment : Fragment() {

    private val signInViewModel = AuthorizationPageViewModel()

    private val emailEditText by lazy {
        view?.findViewById<EditText>(R.id.login_page__email_edit_text)
    }

    private val passwordEditText by lazy {
        view?.findViewById<EditText>(R.id.login_page__password_edit_text)
    }

    private val signInButton by lazy {
        view?.findViewById<Button>(R.id.login_page__enter_button)
    }

    private val backArrowButton by lazy {
        view?.findViewById<Button>(R.id.login_page__arrow_left_button)
    }

    private val registerButton by lazy {
        view?.findViewById<Button>(R.id.login_page__register_button)
    }

    private val register = registerForActivityResult(RegistrationContract()) { user ->
        (activity as AuthorizationActivity).sendResultUser(user)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_login_page, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        signInButton?.setOnClickListener {
            authorize()
        }

        registerButton?.setOnClickListener {
            register.launch(Unit)
        }

        backArrowButton?.setOnClickListener {
            (activity as AuthorizationActivity).sendResultUser(null)
        }

        signInViewModel.authorizationPageState.observe(viewLifecycleOwner) { viewModelState ->
            when (viewModelState) {
                is AuthorizationPageState.Pending -> {
                }
                is AuthorizationPageState.Success -> {
                    (activity as AuthorizationActivity).sendResultUser(viewModelState.user)
                }
                is AuthorizationPageState.Error -> {
                    Toast.makeText(
                        activity,
                        getText(R.string.error_authorization),
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }
    }

    private fun authorize() {
        val userEmail = emailEditText?.text.toString().trim()
        val userPassword = passwordEditText?.text.toString().trim()

        if (userEmail.isEmpty()) {
            emailEditText?.error = getString(R.string.email_required)
            emailEditText?.requestFocus()
            return
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(userEmail).matches()) {
            emailEditText?.error = getString(R.string.valid_email_required)
            emailEditText?.requestFocus()
            return
        }

        if (userPassword.isEmpty()) {
            passwordEditText?.error = getString(R.string.password_required)
            passwordEditText?.requestFocus()
            return
        }

        val user = UserAuthorization(null, null, userEmail, userPassword)
        signInViewModel.authorize(user)
    }
}
