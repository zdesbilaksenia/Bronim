package com.yo.bronim.fragments.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.yo.bronim.R
import com.yo.bronim.contracts.AuthorizationContract
import com.yo.bronim.fragments.home.adapter.MainAdapter
import com.yo.bronim.states.CuisineFiltrationState
import com.yo.bronim.states.HomePageState
import com.yo.bronim.viewmodels.HomePageViewModel

// Not Filtering
const val POPULAR_VIEW_HOLDER_POS = 0
const val KITCHENS_VIEW_HOLDER_POS = 1
const val NEW_VIEW_HOLDER_POS = 2
const val NEAREST_VIEW_HOLDER_POS = 3

// Filtering
const val CUISINE_FILTER_KITCHENS = 0
const val CUISINE_FILTER_FOUND = 1

val CATEGORIES = listOf(
    "Популярное",
    "Кухни",
    "Новое",
    "Все",
)

class HomeFragment : Fragment() {
    private var recycler: RecyclerView? = null
    private var homePageViewModel = HomePageViewModel()

    private val textViewName by lazy {
        view?.findViewById<TextView>(R.id.home__name)
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
        val profileImageView = view.findViewById<ImageView>(R.id.home__profile_image)
        profileImageView.setOnClickListener {
            authorize.launch(Unit)
        }

        recycler = view.findViewById(R.id.main_recycler)
        recycler?.layoutManager = LinearLayoutManager(activity)
        recycler?.adapter = MainAdapter(CATEGORIES.toMutableList(), isFilteringCallback)

        homePageViewModel = HomePageViewModel()

        observePopularRestaurants()
        observeNewRestaurants()
        observeNearestRestaurants()
        observeCuisineFiltration()

        homePageViewModel.getPopularRestaurants()
        homePageViewModel.getNewRestaurants()
        homePageViewModel.getNearestRestaurants()
    }

    private fun observePopularRestaurants() {
        homePageViewModel.recommendedRestaurantsState.observe(viewLifecycleOwner) { state ->
            when (state) {
                // is Pending
                is HomePageState.Success -> {
                    (recycler?.adapter as MainAdapter).updateRestaurants(
                        POPULAR_VIEW_HOLDER_POS,
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
                    (recycler?.adapter as MainAdapter).updateRestaurants(
                        NEW_VIEW_HOLDER_POS,
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
                    (recycler?.adapter as MainAdapter).updateRestaurants(
                        NEAREST_VIEW_HOLDER_POS,
                        state.result
                    )
                }
            }
        }
    }

    private fun observeCuisineFiltration() {
        homePageViewModel.cuisineFiltrationState.observe(viewLifecycleOwner) { state ->
            when (state) {
                is CuisineFiltrationState.Pending -> {
                }
                is CuisineFiltrationState.Success -> {
                    (recycler?.adapter as MainAdapter).updateRestaurants(
                        CUISINE_FILTER_FOUND,
                        state.result
                    )
                }
                is CuisineFiltrationState.Error -> {
                    // TODO error handle
                }
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        val username = textViewName?.text.toString()
        outState.putString(UserNameVariable, username)
    }

    private val isFilteringCallback: (Boolean, String?) -> Unit = { isFiltering, cuisine ->
        (recycler?.adapter as MainAdapter).isFiltering(
            isFiltering
        )
        if (!isFiltering) {
            homePageViewModel.getPopularRestaurants()
            homePageViewModel.getNewRestaurants()
            homePageViewModel.getNearestRestaurants()
        } else {
            homePageViewModel.cuisineFiltration(cuisine!!)
        }
    }

    companion object {
        fun newInstance() = HomeFragment()
        var UserNameVariable = "USERNAME"
    }
}
