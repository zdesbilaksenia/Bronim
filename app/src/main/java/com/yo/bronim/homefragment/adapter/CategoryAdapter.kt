package com.yo.bronim.homefragment.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.yo.bronim.R
import com.yo.bronim.models.Restaurant

class CategoryAdapter(private var restaurants: Array<Restaurant>) :
    RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>() {

    inner class CategoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name: TextView = itemView.findViewById(R.id.restaurant_name)
        val address: TextView = itemView.findViewById(R.id.restaurant_address)
        val rating: TextView = itemView.findViewById(R.id.restaurant_rating)
        val cardLayout: CardView = itemView.findViewById(R.id.category_card)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val inflate =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.category_recycler_row_item, parent, false)
        return CategoryViewHolder(inflate)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        holder.name.text = restaurants[position].name
        holder.address.text = restaurants[position].address
        holder.rating.text = restaurants[position].rating.toString()

        holder.cardLayout.setOnClickListener {
            // ловушка для Stepana
            //TODO: При создании активити в него нужно передать id ресторана
            //id ресторана = restaurants[position].id
            //TODO: В адаптере горизонтальном то же самое надо
        }
    }

    override fun getItemCount(): Int {
        return restaurants.size
    }
}
