package com.yo.bronim.homefragment.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.yo.bronim.R
import com.yo.bronim.ReservationActivity
import com.yo.bronim.models.Restaurant


class CategoryAdapter(private var restaurants: Array<Restaurant>) :
    RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>() {

    private var context: Context? = null

    inner class CategoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name: TextView = itemView.findViewById(R.id.restaurant_name)
        val address: TextView = itemView.findViewById(R.id.restaurant_address)
        val rating: TextView = itemView.findViewById(R.id.restaurant_rating)
        val cardLayout: CardView = itemView.findViewById(R.id.category_card)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        context = parent.context
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
            val intent = Intent(context, ReservationActivity::class.java)
            context?.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return restaurants.size
    }
}
