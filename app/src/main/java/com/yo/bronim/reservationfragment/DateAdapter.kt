package com.yo.bronim.reservationfragment

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.yo.bronim.R
import com.yo.bronim.ReservationActivity

class DateAdapter :
    RecyclerView.Adapter<DateAdapter.DateViewHolder>() {
    inner class DateViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val weekday: TextView = itemView.findViewById(R.id.weekday)
        val date: TextView = itemView.findViewById(R.id.date)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DateViewHolder {
        val inflate =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.date_item, parent, false)
        return DateViewHolder(inflate)
    }

    override fun onBindViewHolder(holder: DateViewHolder, position: Int) {
        holder.weekday.text = "ВТ"
        holder.date.text = "1"
    }

    override fun getItemCount(): Int {
        return 9
    }
}