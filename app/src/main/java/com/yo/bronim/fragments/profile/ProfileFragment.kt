package com.yo.bronim.fragments.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.yo.bronim.ProfileActivity
import com.yo.bronim.R
import com.yo.bronim.models.User
import com.yo.bronim.states.ProfilePageState
import com.yo.bronim.viewmodels.ProfilePageViewModel

class ProfileFragment: Fragment() {

    private var profilePageViewModel = ProfilePageViewModel()

    private val signOutButton by lazy {
        view?.findViewById<TextView>(R.id.profile_page__signOut_button)
    }

    private val saveProfileButton by lazy {
        view?.findViewById<TextView>(R.id.profile_page__save_profile_button)
    }

    private val editTextName by lazy {
        view?.findViewById<TextView>(R.id.profile_page__name_edit_text)
    }

    private val editTextSurname by lazy {
        view?.findViewById<TextView>(R.id.profile_page__surname_edit_text)
    }

    private val editTextDateOfBirth by lazy {
        view?.findViewById<TextView>(R.id.profile_page__date_of_birth_edit_text)
    }

    private val editTextSex by lazy {
        view?.findViewById<TextView>(R.id.profile_page__sex_edit_text)
    }

    private val editTextEmail by lazy {
        view?.findViewById<TextView>(R.id.profile_page__mail_edit_text)
    }

    private val editTextPhoneNumber by lazy {
        view?.findViewById<TextView>(R.id.profile_page__phone_edit_text)
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_profile_page, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        profilePageViewModel = ProfilePageViewModel()

        observeSignOut()
        observeSaveProfile()
        observeGetProfile()

        signOutButton?.setOnClickListener {
            profilePageViewModel.signOut()
        }

        saveProfileButton?.setOnClickListener {
            saveProfile()
        }

        profilePageViewModel.getProfile()

    }


    private fun observeSignOut() {
        profilePageViewModel.signOutState.observe(viewLifecycleOwner) {state ->
            when (state) {
                is ProfilePageState.Success -> {
                    (activity as ProfileActivity).sendResult(null)
                }
                is ProfilePageState.Error -> {
                    Toast.makeText(
                        activity,
                        "Failed to signOut!",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }
    }

    private fun observeSaveProfile() {
        profilePageViewModel.saveProfileState.observe(viewLifecycleOwner) {state ->
            when (state) {
                is ProfilePageState.Success -> {
                    Toast.makeText(
                        activity,
                        "Good Saved",
                        Toast.LENGTH_LONG
                    ).show()
                }
                is ProfilePageState.Error -> {
                    Toast.makeText(
                        activity,
                        "Failed to saveProfile!",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }
    }

    private fun observeGetProfile() {
        profilePageViewModel.getProfileState.observe(viewLifecycleOwner) {state ->
            when (state) {
                is ProfilePageState.Success -> {
                    editTextName?.text = state.user?.name
                    editTextSurname?.text = state.user?.surname
                    editTextDateOfBirth?.text = state.user?.dateOfBirth
                    editTextSex?.text = state.user?.sex
                    editTextEmail?.text = state.user?.email
                    editTextPhoneNumber?.text = state.user?.phoneNumber
                }
                is ProfilePageState.Error -> {
                    editTextName?.text = "Error"
                }
            }
        }
    }

    private fun saveProfile() {
        val name = editTextName?.text.toString().trim()
        val surname = editTextSurname?.text.toString().trim()
        val dateOfBirth = editTextDateOfBirth?.text.toString().trim()
        val sex = editTextSex?.text.toString().trim()
        val email = editTextEmail?.text.toString().trim()
        val phoneNumber = editTextPhoneNumber?.text.toString().trim()

        val user = User(null,name,surname,email,sex,dateOfBirth,phoneNumber)
        profilePageViewModel.saveProfile(user)
    }
}