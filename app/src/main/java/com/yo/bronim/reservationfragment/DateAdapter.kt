package com.yo.bronim.reservationfragment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.yo.bronim.R
import com.yo.bronim.models.Restaurant

class DateAdapter(
    private var dates: MutableList<Pair<String, Int>>,
    private var callback: (date: Pair<String, Int>) -> Unit
) :
    RecyclerView.Adapter<DateAdapter.DateViewHolder>() {

    private var previousChosen: ConstraintLayout? = null

    inner class DateViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val weekday: TextView = itemView.findViewById(R.id.weekday)
        val date: TextView = itemView.findViewById(R.id.date)
        val cell: ConstraintLayout = itemView.findViewById(R.id.date_cell)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DateViewHolder {
        val inflate =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.date_item, parent, false)
        return DateViewHolder(inflate)
    }

    override fun onBindViewHolder(holder: DateViewHolder, position: Int) {
        holder.weekday.text = dates[position].first
        holder.date.text = dates[position].second.toString()

        holder.cell.setOnClickListener {
            previousChosen?.setBackgroundResource(R.drawable.reservation_item_bckgrnd)
            holder.cell.setBackgroundResource(R.drawable.reservation_item_chosen)
            callback(Pair(dates[position].first, dates[position].second))
            previousChosen = holder.cell
        }
    }

    override fun getItemCount(): Int {
        return dates.size
    }
}
