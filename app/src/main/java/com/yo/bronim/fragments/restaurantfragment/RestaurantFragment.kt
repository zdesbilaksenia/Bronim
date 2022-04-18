package com.yo.bronim.fragments.restaurantfragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
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
    private var img : ImageView?=null
    private var name : TextView?=null
    private var address : TextView?=null
    private var description : TextView?=null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_restaurant_page, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

       //TODO: img = view.findViewById<ImageView>(R.id.fragment_restaurant_page__image)
        name = view.findViewById<TextView>(R.id.fragment_restaurant_page__name)
        address = view.findViewById<TextView>(R.id.fragment_restaurant_page__address)
        description = view.findViewById<TextView>(R.id.fragment_restaurant_page__description)
        name?.text = "yo"

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
                is RestaurantPageState.Success -> {
                    //TODO: заполнить поля
                    //img?.img = state.result.img
                    name?.text = state.result.name
                    address?.text = state.result.address
                    description?.text = state.result.description
                }
            }
        }
    }
}
