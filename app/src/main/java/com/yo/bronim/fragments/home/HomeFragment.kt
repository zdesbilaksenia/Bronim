package com.yo.bronim.fragments.home

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.yo.bronim.ProfileActivity
import com.yo.bronim.R
import com.yo.bronim.RegistrationActivity
import com.yo.bronim.contracts.AuthorizationContract
import com.yo.bronim.fragments.home.adapter.MainAdapter
import com.yo.bronim.states.AuthorizationPageState
import com.yo.bronim.states.HomePageState
import com.yo.bronim.viewmodels.AuthorizationPageViewModel
import com.yo.bronim.viewmodels.HomePageViewModel

const val POPULAR_VIEW_HOLDER_POS = 0
const val KITCHENS_VIEW_HOLDER_POS = 1
const val NEW_VIEW_HOLDER_POS = 2
const val NEAREST_VIEW_HOLDER_POS = 3

class HomeFragment : Fragment() {
    private var recycler: RecyclerView? = null
    private var homePageViewModel = HomePageViewModel()
    private var homePageAuthorizationViewModel = AuthorizationPageViewModel()

//    Sample of result activity usage
//    private val register = registerForActivityResult(RegistrationContract()) { name ->
//        textViewName?.text = name
//    }
//    someButton.setOnClickListener {
//            register.launch(Unit)
//        }

    private val textViewName by lazy {
        view?.findViewById<TextView>(R.id.home__name)
    }

    private val profileImageView by lazy {
        view?.findViewById<ImageView>(R.id.home__profile_image)
    }

    private val authorize = registerForActivityResult(AuthorizationContract()) { user ->
        if (user != null) {
            textViewName?.text = user.name
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (savedInstanceState != null) {
            textViewName?.text = savedInstanceState.getString(UserNameVariable)
        }

        recycler = view.findViewById(R.id.main_recycler)
        recycler?.layoutManager = LinearLayoutManager(activity)
        recycler?.adapter = MainAdapter()

        homePageViewModel = HomePageViewModel()

        observePopularRestaurants()
        observeNewRestaurants()
        observeNearestRestaurants()


        homePageViewModel.getPopularRestaurants()
        homePageViewModel.getNewRestaurants()
        homePageViewModel.getNearestRestaurants()

    }

    override fun onStart() {
        super.onStart()
        homePageAuthorizationViewModel = AuthorizationPageViewModel()

        observeIsAuthorized()

        homePageAuthorizationViewModel.isAuthorized()
    }

    private fun observePopularRestaurants() {
        homePageViewModel.recommendedRestaurantsState.observe(viewLifecycleOwner) { state ->
            when (state) {
                // is Pending
                is HomePageState.Success -> {
                    val recommendedHolder = recycler?.findViewHolderForAdapterPosition(
                        POPULAR_VIEW_HOLDER_POS
                    )
                    (recycler?.adapter as MainAdapter).showCategoryRestaurants(
                        recommendedHolder as MainAdapter.MainViewHolder,
                        state.result
                    )
                }
            }
        }
    }

    private fun observeNewRestaurants() {
        homePageViewModel.newRestaurantsState.observe(viewLifecycleOwner) { state ->
            when (state) {
                // is Pending
                is HomePageState.Success -> {
                    val newRestsHolder = recycler?.findViewHolderForAdapterPosition(
                        NEW_VIEW_HOLDER_POS
                    )
                    (recycler?.adapter as MainAdapter).showCategoryRestaurants(
                        newRestsHolder as MainAdapter.MainViewHolder,
                        state.result
                    )
                }
            }
        }
    }

    private fun observeNearestRestaurants() {
        homePageViewModel.nearestRestaurantsState.observe(viewLifecycleOwner) { state ->
            when (state) {
                // is Pending
                is HomePageState.Success -> {
                    val nearestRestsHolder = recycler?.findViewHolderForAdapterPosition(
                        NEAREST_VIEW_HOLDER_POS
                    )
                    (recycler?.adapter as MainAdapter).showNearestRestaurants(
                        nearestRestsHolder as MainAdapter.MainViewHolder,
                        state.result
                    )
                }
            }
        }
    }

    private fun observeIsAuthorized() {
        homePageAuthorizationViewModel.isAuthorizedState.observe(viewLifecycleOwner) { state ->
            when (state){
                is AuthorizationPageState.Success -> {
                    Log.i("observe", "success")
                    profileImageView?.setOnClickListener {
                        val intent = Intent(context, ProfileActivity::class.java)
                        startActivity(intent)
                    }
                }
                is AuthorizationPageState.Error -> {
                    Log.i("observe", "error")
                    profileImageView?.setOnClickListener {
                        authorize.launch(Unit)
                    }
                }
            }

        }
    }

    // сохранение состояния
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        var username = textViewName?.text.toString()
        Log.i("UserName:", username)
        outState.putString(UserNameVariable, username)
    }

    companion object {
        fun newInstance() = HomeFragment()
        var UserNameVariable = "USERNAME"
    }
}
