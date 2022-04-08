package com.yo.bronim.homefragment.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.yo.bronim.R

class MainAdapter : RecyclerView.Adapter<MainAdapter.MainViewHolder>() {
    private var context: Context? = null
    private val tempCategory = arrayOf(
        arrayOf(
            "0",
            "Популярное"
        ),
        arrayOf(
            "1",
            "Кухни"
        ),
        arrayOf(
            "0",
            "Близко к Вам"
        ),
        arrayOf(
            "0",
            "Популярное"
        ),
    )

    inner class MainViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val category: TextView = itemView.findViewById(R.id.category)
        val recyclerView: RecyclerView = itemView.findViewById(R.id.category_recycler)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        context = parent.context
        val inflate =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.home_recycler_category, parent, false)
        return MainViewHolder(inflate)
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        holder.category.text = tempCategory[position][1]
        holder.recyclerView.layoutManager =
            LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
        when (tempCategory[position][0]) {
            "0" -> holder.recyclerView.adapter = CategoryAdapter()
            "1" -> holder.recyclerView.adapter = FilterAdapter()
        }
    }

    override fun getItemCount(): Int {
        return tempCategory.size
    }
}
