package com.yo.bronim.fragments.reservationsListFragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.yo.bronim.R
import com.yo.bronim.fragments.reservationsListFragment.adapter.ReservationAdapter
import com.yo.bronim.states.ReservationsListState
import com.yo.bronim.viewmodels.ReservationsListPageViewModel

class ReservationsListFragment : Fragment() {
    private val reservationsListViewModel = ReservationsListPageViewModel()
    private val recycler by lazy {
        view?.findViewById<RecyclerView>(R.id.fragment_reservations_list__recycler)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_reservations_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recycler?.layoutManager = LinearLayoutManager(context)

        observeReservationsList()
        reservationsListViewModel.getReservationsList()
    }

    private fun observeReservationsList() {
        reservationsListViewModel.reservationsListState.observe(viewLifecycleOwner) { state ->
            when (state) {
                is ReservationsListState.Pending -> {
//                    TODO
                }
                is ReservationsListState.Success -> {
                    recycler?.adapter = ReservationAdapter(state.result)
                }
                is ReservationsListState.Error -> {
//                    TODO
                }
            }
        }
    }

    companion object {
        fun newInstance() = ReservationsListFragment()
    }
}

