package com.yo.bronim.fragments.home.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.yo.bronim.R
import com.yo.bronim.fragments.home.CATEGORIES
import com.yo.bronim.fragments.home.CUISINE_FILTER_FOUND
import com.yo.bronim.fragments.home.CUISINE_FILTER_KITCHENS
import com.yo.bronim.fragments.home.KITCHENS_VIEW_HOLDER_POS
import com.yo.bronim.fragments.home.NEAREST_VIEW_HOLDER_POS
import com.yo.bronim.fragments.home.NEW_VIEW_HOLDER_POS
import com.yo.bronim.fragments.home.POPULAR_VIEW_HOLDER_POS
import com.yo.bronim.models.Restaurant
import java.util.Collections
import kotlin.collections.LinkedHashMap

class MainAdapter(
    private var itemList: MutableList<String>,
    private val isFilteringCallback: (Boolean, String?) -> Unit
) : RecyclerView.Adapter<MainAdapter.MainViewHolder>() {
    private var context: Context? = null
    private var isFiltering: Boolean? = null
    private var payloads: MutableMap<Int, Array<Restaurant>> = LinkedHashMap()
//    var itemsList: Array<Restaurant> = arrayOf()

    inner class MainViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val category: TextView = itemView.findViewById(R.id.category)
        val recyclerView: RecyclerView = itemView.findViewById(R.id.category_recycler)
        val progressBar: ProgressBar = itemView.findViewById(R.id.progress_bar)
        val nothingFoundText: TextView = itemView.findViewById(
            R.id.home_recycler_category__nothing_found_text
        )
        val recycler: ConstraintLayout = itemView.findViewById(
            R.id.home_recycler_category__recycler
        )
        val button: Button = itemView.findViewById(R.id.home_recycler_category__button)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        context = parent.context
        val inflate =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.home_recycler_category, parent, false)
        return MainViewHolder(inflate)
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        holder.category.text = itemList[position]
        Log.d("itemList = ", "$itemList")
        Log.d("pos = ", "$position")
        if (this.isFiltering == null || !this.isFiltering!!) {
            holder.button.visibility = View.GONE
            holder.recyclerView.adapter = null
            holder.progressBar.visibility = View.VISIBLE
            when {
                position == KITCHENS_VIEW_HOLDER_POS -> {
                    holder.progressBar.visibility = View.GONE
                    holder.recyclerView.layoutManager =
                        LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
                    holder.recyclerView.adapter = FilterAdapter(isFilteringCallback)
                }
                position > NEW_VIEW_HOLDER_POS -> {
                    holder.recyclerView.layoutManager =
                        LinearLayoutManager(context, RecyclerView.VERTICAL, false)
                    if (payloads[position] != null) {
                        holder.progressBar.visibility = View.GONE
                        holder.recyclerView.adapter = HorizontalItemAdapter(payloads[position]!!)
                    }
                }

                else -> {
                    holder.recyclerView.layoutManager =
                        LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
                    if (payloads[position] != null) {
                        holder.progressBar.visibility = View.GONE
                        holder.recyclerView.adapter = CategoryAdapter(payloads[position]!!)
                    }
                }
            }
        } else {
            if (position == CUISINE_FILTER_FOUND) {
                holder.progressBar.visibility = View.VISIBLE
                holder.recyclerView.adapter = null
                holder.recyclerView.layoutManager =
                    LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
                holder.button.visibility = View.VISIBLE
                holder.button.setOnClickListener {
                    if (isFiltering != false) {
                        isFilteringCallback(false, null)
                    }
                }
                if (payloads[position] != null) {
                    holder.progressBar.visibility = View.GONE
                    if (payloads[position]?.isEmpty() == true) {
                        holder.nothingFoundText.visibility = View.VISIBLE
                    } else {
                        holder.recyclerView.adapter = CategoryAdapter(payloads[position]!!)
                    }
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    fun updateRestaurants(position: Int, restaurants: Array<Restaurant>) {
        Log.d("UPD", "UPDATING $position")
        payloads[position] = restaurants
        notifyItemChanged(position)
    }

    fun isFiltering(
        isFiltering: Boolean
    ) {
        if (this.isFiltering == isFiltering) {
            notifyItemChanged(1)
            return
        }
        this.isFiltering = isFiltering
        if (isFiltering) {
            payloads = LinkedHashMap()
            Collections.swap(itemList, KITCHENS_VIEW_HOLDER_POS, POPULAR_VIEW_HOLDER_POS)
            notifyItemMoved(KITCHENS_VIEW_HOLDER_POS, POPULAR_VIEW_HOLDER_POS)
            removeAt(NEAREST_VIEW_HOLDER_POS)
            removeAt(NEW_VIEW_HOLDER_POS)
            removeAt(KITCHENS_VIEW_HOLDER_POS)
            insertAt(CUISINE_FILTER_FOUND, "Найдено")
            Log.d("isFiltering", "Struct for true")
        } else {
            payloads = LinkedHashMap()
            removeAt(CUISINE_FILTER_FOUND)
            removeAt(CUISINE_FILTER_KITCHENS)
            for (i in CATEGORIES.indices) {
                Log.d("INDICES = ", "$i")
                insertAt(i, CATEGORIES[i])
            }
            Log.d("isFiltering", "Struct for false")
        }
    }

    private fun removeAt(position: Int) {
        itemList.removeAt(position)
        notifyItemRemoved(position)
        notifyItemRangeChanged(position, itemList.size)
    }

    private fun insertAt(position: Int, element: String) {
        itemList.add(position, element)
        notifyItemInserted(position)
        notifyItemRangeChanged(position, itemList.size)
    }
}
