package com.yo.bronim.fragments.home.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.yo.bronim.R
import com.yo.bronim.models.Restaurant

val CATEGORIES = arrayOf(
    "Популярное",
    "Кухни",
    "Новое",
    "Ближайшие"
)

class MainAdapter : RecyclerView.Adapter<MainAdapter.MainViewHolder>() {
    private var context: Context? = null
    var itemsList: Array<Restaurant> = arrayOf()

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
        holder.category.text = CATEGORIES[position]
        when {
            position == 1 -> {
                holder.recyclerView.layoutManager =
                    LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
                holder.recyclerView.adapter = FilterAdapter()
            }
            position > 2 ->
                holder.recyclerView.layoutManager =
                    LinearLayoutManager(context, RecyclerView.VERTICAL, false)
            else ->
                holder.recyclerView.layoutManager =
                    LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
        }
    }

    override fun getItemCount(): Int {
        return CATEGORIES.size + itemsList.size
    }

    fun showCategoryRestaurants(holder: MainViewHolder, restaurants: Array<Restaurant>) {
        holder.recyclerView.adapter = CategoryAdapter(restaurants)
    }

    fun showNearestRestaurants(holder: MainViewHolder, restaurants: Array<Restaurant>) {
        holder.recyclerView.adapter = HorizontalItemAdapter(restaurants)
    }
}