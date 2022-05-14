package com.yo.bronim.fragments.home.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.view.get
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.yo.bronim.R
import com.yo.bronim.models.Restaurant
import com.yo.bronim.models.kitchens

val CATEGORIES = arrayOf(
    "Популярное",
    "Кухни",
    "Новое",
    "Ближайшие"
)

class MainAdapter : RecyclerView.Adapter<MainAdapter.MainViewHolder>() {
    private var context: Context? = null
    var itemsList: Array<Restaurant> = arrayOf()
    var prevKitchenPos: Int = -1

    inner class MainViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val category: TextView = itemView.findViewById(R.id.category)
        val recyclerView: RecyclerView = itemView.findViewById(R.id.category_recycler)
        val progressBar: ProgressBar = itemView.findViewById(R.id.progress_bar)
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
                holder.progressBar.visibility = View.GONE
                holder.recyclerView.layoutManager =
                    LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
                holder.recyclerView.adapter = FilterAdapter { pos ->
//                    Log.d("MADAPT", "position: $pos")
//                    val h = holder.recyclerView.findViewHolderForAdapterPosition(pos) as FilterAdapter.FilterViewHolder
//                    if (h.chosen) {
//                        h.card.strokeColor = ContextCompat.getColor(context!!, R.color.main_dark_color)
//                        prevKitchenPos = -1
//                    } else {
//                        if (prevKitchenPos != -1) {
//                            val ph = holder.recyclerView.findViewHolderForAdapterPosition(prevKitchenPos) as FilterAdapter.FilterViewHolder
//                            ph.card.strokeColor = ContextCompat.getColor(context!!, R.color.main_dark_color)
//                            ph.chosen = false
//                        }
//                        prevKitchenPos = pos
//                        h.card.strokeColor = ContextCompat.getColor(context!!, R.color.turquoise)
//                    }
//                    h.chosen = !h.chosen
                }
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

    fun showCategoryRestaurants(holder: MainViewHolder, restaurants: Array<Restaurant>, gone: Int) {
        holder.progressBar.visibility = gone
        holder.recyclerView.adapter = CategoryAdapter(restaurants)
    }

    fun showNearestRestaurants(holder: MainViewHolder, restaurants: Array<Restaurant>, gone: Int) {
        holder.progressBar.visibility = gone
        holder.recyclerView.adapter = HorizontalItemAdapter(restaurants)
    }
}
