package com.yo.bronim.fragments.reservationsListFragment.adapter

import android.content.Context
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.yo.bronim.R
import com.yo.bronim.fragments.home.adapter.TAG_MARGIN
import com.yo.bronim.models.ReservationListItem

class ReservationAdapter(private var reservations: Array<ReservationListItem>) : RecyclerView.Adapter<ReservationAdapter.ReservationViewHolder>() {

    private var context: Context? = null
    private var tagsContainer: LinearLayout? = null

    inner class ReservationViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name : TextView = itemView.findViewById(R.id.view_reservation_card__restaurant_name)
        val address : TextView = itemView.findViewById(R.id.view_reservation_card__restaurant_address)
//        val tags:
        val date : TextView = itemView.findViewById(R.id.view_reservation_card__date)
        val time : TextView = itemView.findViewById(R.id.view_reservation_card__time)
        val guestsNumber : TextView = itemView.findViewById(R.id.view_reservation_card__guests_number)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReservationViewHolder {
        context = parent.context
        val inflate = LayoutInflater.from(parent.context)
            .inflate(R.layout.view_reservation_card, parent, false)
        tagsContainer = inflate.findViewById(R.id.view_reservation_card__restaurant_tags)
        return ReservationViewHolder(inflate)
    }

    override fun onBindViewHolder(holder: ReservationViewHolder, position: Int) {
        holder.name.text = reservations[position].name
        holder.address.text = reservations[position].address
        holder.date.text = reservations[position].date
        holder.time.text = reservations[position].time
        reservations[position].tags?.forEach { tag ->
            val textView = setTagParams(tag)
            tagsContainer?.addView(textView)
        }
        (reservations[position].guests_num.toString() + " человек").also { holder.guestsNumber.text = it }
    }

    override fun getItemCount(): Int {
        return reservations.size
    }

    private fun setTagParams(tag: String): TextView {
        val textView = TextView(context, null, 0, R.style.reservations_list_tag_text)
        textView.setBackgroundResource(R.drawable.tag_bckgrnd)
        textView.text = tag
        textView.ellipsize = TextUtils.TruncateAt.END
        textView.maxLines = 1

        val marginParams = ViewGroup.MarginLayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        marginParams.marginEnd = TAG_MARGIN
        textView.layoutParams = marginParams

        return textView
    }
}