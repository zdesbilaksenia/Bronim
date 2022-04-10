package com.yo.bronim.homefragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.yo.bronim.R
import com.yo.bronim.homefragment.adapter.MainAdapter
import com.yo.bronim.states.HomePageState
import com.yo.bronim.viewmodels.HomePageViewModel

class HomeFragment : Fragment() {
    private var recycler: RecyclerView? = null
    private val homePageViewModel = HomePageViewModel()

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

        homePageViewModel.recommendedRestaurantsState.observe(viewLifecycleOwner) { state ->
            when (state) {
                // is Pending
                is HomePageState.Succes -> {
                    val recommendedHolder = recycler?.findViewHolderForAdapterPosition(0)
                    (recycler?.adapter as MainAdapter).showCategoryRestaurants(
                        recommendedHolder as MainAdapter.MainViewHolder,
                        state.result
                    )
                }
            }
        }

        homePageViewModel.newRestaurantsState.observe(viewLifecycleOwner) { state ->
            when (state) {
                // is Pending
                is HomePageState.Succes -> {
                    val newRestsHolder = recycler?.findViewHolderForAdapterPosition(2)
                    (recycler?.adapter as MainAdapter).showCategoryRestaurants(
                        newRestsHolder as MainAdapter.MainViewHolder,
                        state.result
                    )
                }
            }
        }

        homePageViewModel.getRecommendedRestaurants()
        homePageViewModel.getNewRestaurants()
    }

    companion object {
        fun newInstance() = HomeFragment()
    }
}
