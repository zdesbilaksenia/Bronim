package com.yo.bronim.fragments.home.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.card.MaterialCardView
import com.yo.bronim.R
import com.yo.bronim.models.kitchens

class FilterAdapter(
    private val isFilteringCallback: (Boolean, String?) -> Unit
) : RecyclerView.Adapter<FilterAdapter.FilterViewHolder>(), View.OnClickListener {

    private var context: Context? = null
    private var prevHolder: FilterViewHolder? = null

    private var selectedPos: Int? = null

    inner class FilterViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val kitchen: TextView = itemView.findViewById(R.id.kitchen)
        val card: MaterialCardView = itemView.findViewById(R.id.kitchen_card)
        val icon: ImageView = itemView.findViewById(R.id.kitchen_icon)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilterViewHolder {
        context = parent.context
        val inflate =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.filter_recycler_row_item, parent, false)
        return FilterViewHolder(inflate)
    }

    override fun onBindViewHolder(holder: FilterViewHolder, position: Int) {
        val kitchen = kitchens[position]
        holder.kitchen.text = kitchen.name
        holder.icon.setImageDrawable(
            ContextCompat.getDrawable(context!!, kitchen.icon)
        )

        if (position != selectedPos) {
            holder.card.strokeColor = ContextCompat.getColor(context!!, R.color.main_dark_color)
        } else {
            holder.card.strokeColor = ContextCompat.getColor(context!!, R.color.turquoise)
        }
        holder.card.setOnClickListener {
            holder.card.strokeColor = ContextCompat.getColor(context!!, R.color.turquoise)
            selectedPos = holder.adapterPosition
            prevHolder?.card?.strokeColor = ContextCompat.getColor(
                context!!,
                R.color.main_dark_color
            )
            if (prevHolder == holder) {
                prevHolder = null
                isFilteringCallback(false, null)
            } else {
                isFilteringCallback(true, holder.kitchen.text.toString())
                prevHolder = holder
            }
        }
    }

    override fun getItemCount(): Int {
        return kitchens.size
    }

    override fun onClick(view: View?) {
        if (selectedPos != null) {
            notifyItemChanged(selectedPos!!)
        }
    }
}
