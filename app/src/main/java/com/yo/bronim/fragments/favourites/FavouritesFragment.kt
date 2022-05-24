package com.yo.bronim.fragments.favourites

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.yo.bronim.AuthorizationActivity
import com.yo.bronim.R
import com.yo.bronim.fragments.home.adapter.HorizontalItemAdapter
import com.yo.bronim.states.FavouritesPageState
import com.yo.bronim.viewmodels.FavouritesPageViewModel

class FavouritesFragment : Fragment() {

    private var recycler: RecyclerView? = null
    private var favouritesPageViewModel = FavouritesPageViewModel()

    private val textViewName by lazy {
        view?.findViewById<TextView>(R.id.favourites_page__favourites_textview)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_favourites, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recycler = view.findViewById(R.id.favourites_page_favourites_recycler)
        recycler?.layoutManager = LinearLayoutManager(activity)

        favouritesPageViewModel = FavouritesPageViewModel()

        observeFavouritesRestaurants()

        favouritesPageViewModel.getFavouritesRestaurants()
    }

    private fun observeFavouritesRestaurants() {
        favouritesPageViewModel.favouritesRestaurantsState.observe(viewLifecycleOwner) { state ->
            when (state) {
                is FavouritesPageState.Success -> {
                    recycler?.adapter = HorizontalItemAdapter(state.result)
                }
                is FavouritesPageState.Error -> {
                    Log.e("favourites", state.error.toString())
                    startActivity(Intent(context, AuthorizationActivity::class.java))
                }
            }
        }
    }

    companion object {
        fun newInstance() = FavouritesFragment()
    }
}
