package com.yo.bronim.fragments.favourites

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.yo.bronim.R
import com.yo.bronim.fragments.home.adapter.HorizontalItemAdapter
import com.yo.bronim.models.Restaurant
import com.yo.bronim.states.FavouritesPageState
import com.yo.bronim.viewmodels.FavouritesPageViewModel

class FavouritesFragment : Fragment() {

    private var recycler: RecyclerView? = null
    private var favouritesPageViewModel = FavouritesPageViewModel()

    private val textViewName by lazy {
        view?.findViewById<TextView>(R.id.favourites_page__favourites_textview)
    }

    private val pleaseAuthTextView by lazy {
        view?.findViewById<TextView>(R.id.favourites_page_please_auth_textView)
    }

    private val progressBar by lazy {
        view?.findViewById<ProgressBar>(R.id.favourites_page__loader)
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
    }

    override fun onStart() {
        super.onStart()
        recycler?.layoutManager = LinearLayoutManager(activity)

        favouritesPageViewModel = FavouritesPageViewModel()

        observeFavouritesRestaurants()

        favouritesPageViewModel.getFavouritesRestaurants()
    }

    private fun observeFavouritesRestaurants() {
        pleaseAuthTextView?.visibility = (View.INVISIBLE)
        favouritesPageViewModel.favouritesRestaurantsState.observe(viewLifecycleOwner) { state ->
            when (state) {
                is FavouritesPageState.Success -> {
                    progressBar?.visibility = View.GONE
                    recycler?.adapter = HorizontalItemAdapter(state.result)
                }
                is FavouritesPageState.Error -> {
                    progressBar?.visibility = View.GONE
                    Log.e("favourites", state.error.toString())
                    recycler?.adapter = HorizontalItemAdapter(emptyArray<Restaurant>())
                    pleaseAuthTextView?.visibility = (View.VISIBLE)
                }
            }
        }
    }

    companion object {
        fun newInstance() = FavouritesFragment()
    }
}
