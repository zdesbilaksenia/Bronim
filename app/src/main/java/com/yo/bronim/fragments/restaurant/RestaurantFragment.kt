package com.yo.bronim.fragments.restaurant

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.yo.bronim.R
import com.yo.bronim.states.RestaurantPageState
import com.yo.bronim.viewmodels.RestaurantPageViewModel

class RestaurantFragment : Fragment() {
    private var restaurantPageViewModel = RestaurantPageViewModel()
    private var img : ImageView?=null
    private var name : TextView?=null
    private var address : TextView?=null
    private var description : TextView?=null
    private var restaurantID : Int?=0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        restaurantID = arguments?.getInt("restaurantID")
        return inflater.inflate(R.layout.fragment_restaurant_page, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        img = view.findViewById<ImageView>(R.id.fragment_restaurant_page__image)
        name = view.findViewById<TextView>(R.id.fragment_restaurant_page__name)
        address = view.findViewById<TextView>(R.id.fragment_restaurant_page__address)
        description = view.findViewById<TextView>(R.id.fragment_restaurant_page__description)

        restaurantPageViewModel = RestaurantPageViewModel()

        observeRestaurant()
        restaurantPageViewModel.getRestaurant(restaurantID!!)
    }

    private fun observeRestaurant() {
        restaurantPageViewModel.restaurantState.observe(viewLifecycleOwner) { state ->
            when (state) {
                is RestaurantPageState.Success -> {
                    Glide.with(this).load(state.result.img).into(img!!);
                    name?.text = state.result.name
                    address?.text = state.result.address
                    description?.text = state.result.description
                }
            }
        }
    }
}
