package com.yo.bronim.signInFragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.google.android.gms.common.SignInButton
import com.yo.bronim.R
import com.yo.bronim.models.UserAuthorization
import com.yo.bronim.states.AuthorizationPageState
import com.yo.bronim.viewmodels.AuthorizationPageViewModel

class SignInFragment : Fragment() {

    private val SignInViewModel = AuthorizationPageViewModel()

    private val EmailEditText by lazy {
        view?.findViewById<EditText>(R.id.login_page__email_edit_text)
    }

    private val PasswordEditText by lazy {
        view?.findViewById<EditText>(R.id.login_page__password_edit_text)
    }

    private val SignInButton by lazy {
        view?.findViewById<Button>(R.id.login_page__enter_button)
    }

    private val ErrorMessage by lazy {
        view?.findViewById<TextView>(R.id.login_page__error_text_view)
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

        SignInButton?.setOnClickListener{
            register()
        }

        SignInViewModel.authorizationPageState.observe(viewLifecycleOwner) { viewModelState ->
            when(viewModelState) {
                is AuthorizationPageState.Pending -> {

                }
                is AuthorizationPageState.Success -> {

                }
                is AuthorizationPageState.Error -> {

                }
            }

        }
    }

    private fun register() {
        val userEmail = EmailEditText?.text.toString().trim()
        val userPassword = PasswordEditText?.text.toString().trim()
        if (userEmail.isEmpty()) {
            ErrorMessage?.setText("Email is Empty")
            return
        }

        if (userPassword.isEmpty()) {
            ErrorMessage?.setText("Password is empty")
            return
        }

        var user = UserAuthorization(userEmail, userPassword)
        SignInViewModel.authorize(user)
        ErrorMessage?.setText("Good")
    }
}