package com.yo.bronim.fragments.restaurantfragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.yo.bronim.R
import com.yo.bronim.homefragment.adapter.MainAdapter
import com.yo.bronim.states.RestaurantPageState
import com.yo.bronim.viewmodels.RestaurantPageViewModel

const val POPULAR_VIEW_HOLDER_POS = 0
const val KITCHENS_VIEW_HOLDER_POS = 1
const val NEW_VIEW_HOLDER_POS = 2
const val NEAREST_VIEW_HOLDER_POS = 3

class RestaurantFragment : Fragment() {
    private var restaurantPageViewModel = RestaurantPageViewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_restaurant_page, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        restaurantPageViewModel = RestaurantPageViewModel()

        val restaurantIDDebug = 1

        //TODO: передавать ID ресторана
        observeRestaurant(restaurantIDDebug)

        //TODO: передавать ID ресторана
        restaurantPageViewModel.getRestaurant(restaurantIDDebug)
    }

    private fun observeRestaurant(restaurantID: Int) {
        restaurantPageViewModel.restaurantState.observe(viewLifecycleOwner) { state ->
            when (state) {
                // is Pending
                is RestaurantPageState.Success -> {
                }
            }
        }
    }

    companion object {
        fun newInstance() = RestaurantFragment()
    }
}
