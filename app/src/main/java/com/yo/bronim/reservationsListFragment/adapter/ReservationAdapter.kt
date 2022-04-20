package com.yo.bronim.reservationsListFragment.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.yo.bronim.R
import com.yo.bronim.models.Reservation
import com.yo.bronim.models.ReservationListItem
import org.w3c.dom.Text

class ReservationAdapter(private var reservations: Array<ReservationListItem>) : RecyclerView.Adapter<ReservationAdapter.ReservationViewHolder>() {
    inner class ReservationViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
//        val reservationCard : CardView = itemView.findViewById(R.id.view_reservation_card__card)
        val name : TextView = itemView.findViewById(R.id.view_reservation_card__restaurant_name)
        val address : TextView = itemView.findViewById(R.id.view_reservation_card__restaurant_address)
//        val tags:
        val date : TextView = itemView.findViewById(R.id.view_reservation_card__date)
        val time : TextView = itemView.findViewById(R.id.view_reservation_card__time)
        val guestsNumber : TextView = itemView.findViewById(R.id.view_reservation_card__guests_number)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReservationViewHolder {
        val inflate = LayoutInflater.from(parent.context)
            .inflate(R.layout.view_reservation_card, parent, false)
        return ReservationViewHolder(inflate)
    }

    override fun onBindViewHolder(holder: ReservationViewHolder, position: Int) {
        holder.name.text = reservations[position].name
        holder.address.text = reservations[position].address
//        holder.tags.text = reservations[position].tags
        holder.date.text = reservations[position].date
        holder.time.text = reservations[position].time
        (reservations[position].guests_num.toString() + " человек").also { holder.guestsNumber.text = it }
    }

    override fun getItemCount(): Int {
        return reservations.size
    }

}