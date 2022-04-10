package com.yo.bronim.homefragment.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.yo.bronim.R

class FilterAdapter : RecyclerView.Adapter<FilterAdapter.FilterViewHolder>() {


    inner class FilterViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val kitchen: TextView = itemView.findViewById(R.id.kitchen)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilterViewHolder {
        val inflate =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.filter_recycler_row_item, parent, false)
        return FilterViewHolder(inflate)
    }

    override fun onBindViewHolder(holder: FilterViewHolder, position: Int) {
//        holder.kitchen.text = kitchens[position]
    }

    override fun getItemCount(): Int {
        return 1
    }
}
