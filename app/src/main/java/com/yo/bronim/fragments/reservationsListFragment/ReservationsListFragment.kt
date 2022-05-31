package com.yo.bronim.fragments.reservationsListFragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.yo.bronim.AuthorizationActivity
import com.yo.bronim.R
import com.yo.bronim.fragments.reservationsListFragment.adapter.ReservationAdapter
import com.yo.bronim.states.ReservationsListState
import com.yo.bronim.viewmodels.ReservationsListPageViewModel

class ReservationsListFragment : Fragment() {
    private val reservationsListViewModel = ReservationsListPageViewModel()
    private val recycler by lazy {
        view?.findViewById<RecyclerView>(R.id.fragment_reservations_list__recycler)
    }
    private val loader by lazy {
        view?.findViewById<ProgressBar>(R.id.fragment_reservations_list__loader)
    }
    private val messageText by lazy {
        view?.findViewById<TextView>(R.id.fragment_reservations_list__message_text)
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
        observeReservationsList()
    }

    override fun onStart() {
        super.onStart()
        messageText?.visibility = View.GONE
        recycler?.visibility = View.GONE
        loader?.visibility = View.GONE

        val user = AuthorizationActivity.getFBUser()
        if (user == null) {
            messageText?.visibility = View.VISIBLE
            messageText?.text = getString(R.string.reservations_list_please_auth)
            return
        }
        reservationsListViewModel.getReservationsList()
    }

    private fun observeReservationsList() {
        reservationsListViewModel.reservationsListState.observe(viewLifecycleOwner) { state ->
            when (state) {
                is ReservationsListState.Pending -> {
                    loader?.visibility = View.VISIBLE
                }
                is ReservationsListState.Success -> {
                    loader?.visibility = View.GONE
                    if (state.result == null || state.result.isEmpty()) {
                        messageText?.visibility = View.VISIBLE
                        messageText?.text = getString(R.string.no_reservations)
                    } else {
                        recycler?.visibility = View.VISIBLE
                        recycler?.layoutManager = LinearLayoutManager(context)
                        recycler?.adapter = ReservationAdapter(state.result)
                    }
                }
                is ReservationsListState.Error -> {
                    messageText?.visibility = View.VISIBLE
                    messageText?.text = getString(R.string.error_message)
                }
            }
        }
    }

    companion object {
        fun newInstance() = ReservationsListFragment()
    }
}
