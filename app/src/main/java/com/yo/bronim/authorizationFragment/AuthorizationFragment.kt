package com.yo.bronim.authorizationFragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.yo.bronim.AuthorizationActivity
import com.yo.bronim.R
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

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_login_page, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        signInButton?.setOnClickListener{
            authorize()
        }


        signInViewModel.authorizationPageState.observe(viewLifecycleOwner) { viewModelState ->
            when(viewModelState) {
                is AuthorizationPageState.Pending -> {

                }
                is AuthorizationPageState.Success -> {
                    (activity as AuthorizationActivity).sendResultUser(viewModelState.user)
                }
                is AuthorizationPageState.Error -> {
                    Toast.makeText(
                        activity,
                        "Error",
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
            emailEditText?.error = "Email is required!"
            emailEditText?.requestFocus()
            return
        }


        if (userPassword.isEmpty()) {
            passwordEditText?.error = "Password is required!"
            passwordEditText?.requestFocus()
            return
        }


        val user = UserAuthorization(null, null, userEmail, userPassword)
        signInViewModel.authorize(user)

    }

}

