package com.yo.bronim.signInFragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import com.yo.bronim.MainActivity
import com.yo.bronim.R
import com.yo.bronim.models.UserAuthorization
import com.yo.bronim.states.AuthorizationPageState
import com.yo.bronim.viewmodels.AuthorizationPageViewModel

class SignInFragment : Fragment() {

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

    private val errorMessage by lazy {
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
        signInButton?.setOnClickListener{
            register()
        }

        signInViewModel.authorizationPageState.observe(viewLifecycleOwner) { viewModelState ->
            when(viewModelState) {
                is AuthorizationPageState.Pending -> {

                }
                is AuthorizationPageState.Success -> {
                    val intent = Intent(activity, MainActivity::class.java)
                    startActivity(intent)
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

    private fun register() {
        val userEmail = emailEditText?.text.toString().trim()

        val userPassword = passwordEditText?.text.toString().trim()
        if (userEmail.isEmpty()) {
            errorMessage?.text = "Email is Empty"
            return
        }

        if (userPassword.isEmpty()) {
            errorMessage?.text = "Password is empty"
            return
        }

        Log.i("DEBUG: UserEmail: ",userEmail)
        Log.i("DEBUG: UserPassword: ",userPassword)
        val user = UserAuthorization(null, null, userEmail, userPassword)
        signInViewModel.authorize(user)
        errorMessage?.text = "Good"
    }
}