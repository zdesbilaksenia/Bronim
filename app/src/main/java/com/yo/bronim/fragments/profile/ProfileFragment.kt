package com.yo.bronim.fragments.profile

import android.os.Bundle
import android.provider.ContactsContract
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.yo.bronim.ProfileActivity
import com.yo.bronim.R
import com.yo.bronim.states.ProfilePageState
import com.yo.bronim.viewmodels.HomePageViewModel
import com.yo.bronim.viewmodels.ProfilePageViewModel

class ProfileFragment: Fragment() {

    private var profilePageViewModel = ProfilePageViewModel()

    private val signOutButton by lazy {
        view?.findViewById<TextView>(R.id.profile_page__signOut_button)
    }

    private val saveProfileButton by lazy {
        view?.findViewById<TextView>(R.id.profile_page__save_profile_button)
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

        signOutButton?.setOnClickListener {
            profilePageViewModel.signOut()
        }

        saveProfileButton?.setOnClickListener {
            profilePageViewModel.saveProfile()
        }

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
}