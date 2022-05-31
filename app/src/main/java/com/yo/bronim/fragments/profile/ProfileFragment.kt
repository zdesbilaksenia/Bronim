package com.yo.bronim.fragments.profile

import android.content.Context
import android.os.Bundle
import android.util.Patterns
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.yo.bronim.ProfileActivity
import com.yo.bronim.R
import com.yo.bronim.models.User
import com.yo.bronim.states.ProfilePageState
import com.yo.bronim.viewmodels.ProfilePageViewModel

class ProfileFragment : Fragment() {

    private var progressBar: ProgressBar? = null
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

    private val backButton by lazy {
        view?.findViewById<Button>(R.id.profile_page__arrow_left_button)
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

        progressBar = view.findViewById(R.id.progress_bar)
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

        backButton?.setOnClickListener {
            activity?.finish()
        }

        profilePageViewModel.getProfile()
    }

    private fun observeSignOut() {
        profilePageViewModel.signOutState.observe(viewLifecycleOwner) { state ->
            when (state) {
                is ProfilePageState.Success -> {
                    progressBar?.visibility = View.GONE
                    (activity as ProfileActivity).sendResult(null)
                }
                is ProfilePageState.Error -> {
                    progressBar?.visibility = View.GONE
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
        profilePageViewModel.saveProfileState.observe(viewLifecycleOwner) { state ->
            when (state) {
                is ProfilePageState.Success -> {
                    progressBar?.visibility = View.GONE
                    var layout: View? = null
                    layout = layoutInflater.inflate(R.layout.toast_ok, null)

                    val toast = Toast(activity?.applicationContext)
                    toast.setGravity(Gravity.FILL, 0, 0)
                    toast.duration = Toast.LENGTH_SHORT
                    toast.view = layout
                    toast.show()
                }
                is ProfilePageState.Error -> {
                    progressBar?.visibility = View.GONE
                    var layout: View? = null
                    layout = layoutInflater.inflate(R.layout.toast_error, null)

                    val toast = Toast(activity?.applicationContext)
                    toast.setGravity(Gravity.FILL, 0, 0)
                    toast.duration = Toast.LENGTH_SHORT
                    toast.view = layout
                    toast.show()
                }
            }
        }
    }

    private fun observeGetProfile() {
        profilePageViewModel.getProfileState.observe(viewLifecycleOwner) { state ->
            when (state) {
                is ProfilePageState.Success -> {
                    progressBar?.visibility = View.GONE
                    editTextName?.text = state.user?.name
                    editTextSurname?.text = state.user?.surname
                    editTextDateOfBirth?.text = state.user?.dateOfBirth
                    editTextSex?.text = state.user?.sex
                    editTextEmail?.text = state.user?.email
                    editTextPhoneNumber?.text = state.user?.phoneNumber
                }
                is ProfilePageState.Error -> {
                    progressBar?.visibility = View.GONE
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

        if (name.isEmpty()) {
            editTextName?.error = getString(R.string.name_required)
            editTextName?.requestFocus()
            return
        }

        if (email.isEmpty()) {
            editTextEmail?.error = getString(R.string.email_required)
            editTextEmail?.requestFocus()
            return
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            editTextEmail?.error = getString(R.string.valid_email_required)
            editTextEmail?.requestFocus()
            return
        }

        if (!Patterns.PHONE.matcher(phoneNumber).matches()) {
            editTextPhoneNumber?.error = getString(R.string.valid_phone_required)
            editTextPhoneNumber?.requestFocus()
            return
        }

        val user = User(null, name, surname, email, sex, dateOfBirth, phoneNumber)
        profilePageViewModel.saveProfile(user)
    }
}
