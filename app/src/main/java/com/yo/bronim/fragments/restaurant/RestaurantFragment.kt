package com.yo.bronim.fragments.restaurant

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.yo.bronim.AuthorizationActivity
import com.yo.bronim.R
import com.yo.bronim.ReservationActivity
import com.yo.bronim.contracts.AuthorizationContract
import com.yo.bronim.models.Restaurant
import com.yo.bronim.states.FavouritesPageState
import com.yo.bronim.states.RestaurantPageState
import com.yo.bronim.states.SubscribeState
import com.yo.bronim.viewmodels.FavouritesPageViewModel
import com.yo.bronim.viewmodels.RestaurantPageViewModel

class RestaurantFragment : Fragment() {
    private var restaurantPageViewModel = RestaurantPageViewModel()
    private var favouritesViewModel = FavouritesPageViewModel()
    private var img: ImageView? = null
    private var name: TextView? = null
    private var address: TextView? = null
    private var description: TextView? = null
    private var restaurantID: Int? = 0
    var bundle: Bundle? = Bundle()
    private var restaurant: Restaurant? = null

    private val favouriteButton by lazy {
        view?.findViewById<ImageView>(R.id.fragment_restaurant_page__btn_favourite)
    }

    private val authorize = registerForActivityResult(AuthorizationContract()) { }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_restaurant_page, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        img = view.findViewById<ImageView>(R.id.fragment_restaurant_page__image)
        name = view.findViewById<TextView>(R.id.fragment_restaurant_page__name)
        address = view.findViewById<TextView>(R.id.fragment_restaurant_page__address)
        description = view.findViewById<TextView>(R.id.fragment_restaurant_page__description)

        restaurantPageViewModel = RestaurantPageViewModel()
        favouritesViewModel = FavouritesPageViewModel()

        observeRestaurant()
        observeFavourites()
        observeSubscribe()
        observeUnsubscribe()

        restaurantPageViewModel.getRestaurant(restaurantID)
        favouritesViewModel.getFavouritesRestaurants()

        val back = view.findViewById<ImageView>(R.id.fragment_restaurant_page__btn_back)
        back.setOnClickListener {
            activity?.finish()
        }

        favouriteButton?.setOnClickListener {
            favouritesViewModel.subscribe(restaurantID)
        }

        val makeReservation = view.findViewById<Button>(R.id.make_reservation_btn)
        makeReservation.setOnClickListener {
            val user = AuthorizationActivity.getFBUser()
            if (user == null) {
                authorize.launch(Unit)
            } else {
                val intent = Intent(context, ReservationActivity::class.java)
                intent.putExtra("start", restaurant?.start)
                intent.putExtra("end", restaurant?.end)
                intent.putExtra("id", restaurant?.id)
                context?.startActivity(intent)
            }
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        restaurantID = bundle?.getInt("restaurantID")
    }

    private fun observeRestaurant() {
        restaurantPageViewModel.restaurantState.observe(viewLifecycleOwner) { state ->
            when (state) {
                is RestaurantPageState.Success -> {
                    restaurant = state.result
                    Glide.with(this).load(state.result.img).into(img!!)
                    name?.text = state.result.name
                    address?.text = state.result.address
                    description?.text = state.result.description
                }
            }
        }
    }

    private fun observeFavourites() {
        favouritesViewModel.favouritesRestaurantsState.observe(viewLifecycleOwner) { state ->
            when (state) {
                is FavouritesPageState.Success -> {
                    var rests = state.result
                    for (rest in rests) {
                        if (rest.id == restaurantID) {
                            favouriteButton?.setImageDrawable(
                                resources.getDrawable(R.drawable.ic_fav_true)
                            )
                            favouriteButton?.setOnClickListener {
                                favouritesViewModel.unsubscribe(restaurantID)
                            }
                            break
                        }
                    }
                }
            }
        }
    }

    private fun observeSubscribe() {
        favouritesViewModel.subscribeState.observe(viewLifecycleOwner) { state ->
            when (state) {
                is SubscribeState.Success -> {
                    favouriteButton?.setImageDrawable(
                        resources.getDrawable(R.drawable.ic_fav_true)
                    )
                    favouriteButton?.setOnClickListener {
                        favouritesViewModel.unsubscribe(restaurantID)
                    }
                }
            }
        }
    }

    private fun observeUnsubscribe() {
        favouritesViewModel.unsubscribeState.observe(viewLifecycleOwner) { state ->
            when (state) {
                is SubscribeState.Success -> {
                    favouriteButton?.setImageDrawable(resources.getDrawable(R.drawable.ic_fav))
                    favouriteButton?.setOnClickListener {
                        favouritesViewModel.subscribe(restaurantID)
                    }
                }
            }
        }
    }

    companion object {
        fun newInstance(restaurantID: Int) = RestaurantFragment().apply {
            bundle?.putInt("restaurantID", restaurantID)
        }
    }
}
