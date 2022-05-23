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
import androidx.core.view.get
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.yo.bronim.R
import com.yo.bronim.fragments.home
import com.yo.bronim.models.Restaurant
import java.util.*
import kotlin.collections.LinkedHashMap

class MainAdapter(
    private var itemList: MutableList<String>,
    private val isFilteringCallback: (Boolean) -> Unit
) : RecyclerView.Adapter<MainAdapter.MainViewHolder>() {
    private var context: Context? = null
    private var isFiltering: Boolean? = null
    private var payloads: MutableMap<Int, Array<Restaurant>> = LinkedHashMap()
//    var itemsList: Array<Restaurant> = arrayOf()

    inner class MainViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val category: TextView = itemView.findViewById(R.id.category)
        val recyclerView: RecyclerView = itemView.findViewById(R.id.category_recycler)
        val progressBar: ProgressBar = itemView.findViewById(R.id.progress_bar)
        val recycler: ConstraintLayout = itemView.findViewById(
            R.id.home_recycler_category__recycler
        )
        val button: Button = itemView.findViewById(R.id.home_recycler_category__button)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        Log.d("MAINADAPT", "ONCREATEVIEWHOLDERRRRRRRRRRR")
        context = parent.context
        val inflate =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.home_recycler_category, parent, false)
        val holder = MainViewHolder(inflate)
        return holder
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        Log.d("ONBINDVH", "W/O PAYLOAD")
        Log.d("itemList", "$itemList")
        Log.d("payloads", "${payloads[position]}")
        holder.category.text = itemList[position]
        Log.d("category = ", itemList[position])
        Log.d("position = ", "$position")
        Log.d("is filtering?", "$isFiltering")
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
            if (position == 0) {
//                holder.progressBar.visibility = View.GONE
//                holder.recyclerView.layoutManager =
//                    LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
//                holder.recyclerView.adapter = FilterAdapter(isFilteringCallback)
            } else {
                holder.progressBar.visibility = View.VISIBLE
                holder.recyclerView.adapter = null
                holder.recyclerView.layoutManager =
                    LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
                holder.button.visibility = View.VISIBLE
                holder.button.setOnClickListener {
                    if (isFiltering != false) {
                        isFilteringCallback(false)
                    }
                }
            }
        }

//        if (position == CUISINE_FILTER_FOUND) {
//            holder.recycler.visibility = View.GONE
//        }
    }

//    override fun onBindViewHolder(holder: MainViewHolder, position: Int, payload: MutableList<Any>) {
//        if (!payload.isEmpty()) {
//            Log.d("ONBINDVH", "W/ PAYLOAD pos: $position")
//            val rests = payload[0] as Array<Restaurant>
//            payloads[position] = rests
//        }
//        return onBindViewHolder(holder, position)
//        holder.progressBar.visibility = View.GONE
//        if (position == NEAREST_VIEW_HOLDER_POS) {
//            holder.recyclerView.adapter = HorizontalItemAdapter(rests)
//        } else {
//            holder.recyclerView.adapter = CategoryAdapter(rests)
//        }
//    }

    override fun getItemCount(): Int {
//        return CATEGORIES.size + itemsList.size
        return itemList.size
    }

//    override fun getItemViewType(position: Int): Int {
//        return position
//    }

    fun updateRestaurants(position: Int, restaurants: Array<Restaurant>) {
        Log.d("UPD", "UPDATING $position")
        payloads[position] = restaurants
        notifyItemChanged(position)
    }

    fun showCategoryRestaurants(holder: MainViewHolder?, restaurants: Array<Restaurant>, gone: Int) {
        holder?.progressBar?.visibility = gone
        holder?.recyclerView?.adapter = CategoryAdapter(restaurants)
    }

    fun showNearestRestaurants(holder: MainViewHolder?, restaurants: Array<Restaurant>, gone: Int) {
        holder?.progressBar?.visibility = gone
        holder?.recyclerView?.adapter = HorizontalItemAdapter(restaurants)
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
            Collections.swap(itemList, 1, 0)
            notifyItemMoved(1, 0)
            removeAt(3)
            removeAt(2)
            removeAt(1)
            insertAt(1, "Найдено")
            Log.d("isFiltering", "Struct for true")
        } else {
            payloads = LinkedHashMap()
            removeAt(1)
            removeAt(0)
            for (i in CATEGORIES.indices) {
                Log.d("INDICES = ", "$i")
                insertAt(i, CATEGORIES[i])
            }
            Log.d("isFiltering", "Struct for false")
        }
//        this.notifyItemChanged(POPULAR_VIEW_HOLDER_POS)
//        this.notifyItemChanged(NEW_VIEW_HOLDER_POS)
//        this.notifyItemChanged(NEAREST_VIEW_HOLDER_POS)
//        this.notifyItemChanged(CUISINE_FILTER_FOUND)
    }

    fun removeAt(position: Int) {
        itemList.removeAt(position)
        notifyItemRemoved(position)
        notifyItemRangeChanged(position, itemList.size)
    }

    fun insertAt(position: Int, element: String) {
        itemList.add(position, element)
        notifyItemInserted(position)
        notifyItemRangeChanged(position, itemList.size)
    }
}
