package com.yo.bronim.fragments.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.yo.bronim.R
import com.yo.bronim.fragments.home.adapter.MainAdapter
import com.yo.bronim.states.HomePageState
import com.yo.bronim.viewmodels.HomePageViewModel

const val POPULAR_VIEW_HOLDER_POS = 0
const val KITCHENS_VIEW_HOLDER_POS = 1
const val NEW_VIEW_HOLDER_POS = 2
const val NEAREST_VIEW_HOLDER_POS = 3

class HomeFragment : Fragment() {
    private var recycler: RecyclerView? = null
    private var homePageViewModel = HomePageViewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recycler = view.findViewById(R.id.main_recycler)
        recycler?.layoutManager = LinearLayoutManager(context)
        recycler?.adapter = MainAdapter()

        homePageViewModel = HomePageViewModel()

        observePopularRestaurants()
        observeNewRestaurants()
        observeNearestRestaurants()

        homePageViewModel.getPopularRestaurants()
        homePageViewModel.getNewRestaurants()
        homePageViewModel.getNearestRestaurants()
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

    companion object {
        fun newInstance() = HomeFragment()
    }
}
