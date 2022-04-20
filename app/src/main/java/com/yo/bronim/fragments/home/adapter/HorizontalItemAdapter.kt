package com.yo.bronim.fragments.home.adapter

import android.content.Context
import android.content.Intent
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RatingBar
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.yo.bronim.R
import com.yo.bronim.RestaurantActivity
import com.yo.bronim.models.Restaurant

const val TAG_MARGIN = 16

class HorizontalItemAdapter(private var restaurants: Array<Restaurant>) :
    RecyclerView.Adapter<HorizontalItemAdapter.HorizontalItemViewHolder>() {

    private var context: Context? = null
    private var tagsContainer: LinearLayout? = null

    inner class HorizontalItemViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        val name: TextView = itemView.findViewById(R.id.restaurant_name)
        val address: TextView = itemView.findViewById(R.id.restaurant_address)
        val rating: RatingBar = itemView.findViewById(R.id.restaurant_rating)
        val cardLayout: CardView = itemView.findViewById(R.id.horizontal_card)
        val image: ImageView = itemView.findViewById(R.id.restaurant_image)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HorizontalItemViewHolder {
        context = parent.context
        val inflate =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.home_recycler_horizontal_item, parent, false)
        tagsContainer = inflate.findViewById(R.id.restaurant_tags)
        return HorizontalItemViewHolder(inflate)
    }

    override fun onBindViewHolder(holder: HorizontalItemViewHolder, position: Int) {
        holder.name.text = restaurants[position].name
        holder.address.text = restaurants[position].address
        holder.rating.rating = restaurants[position].rating


        Glide.with(context!!).load(restaurants[position].img).into(holder.image);

        restaurants[position].tags?.forEach {
            val textView = setTagParams(it)
            tagsContainer?.addView(textView)
        }

        holder.cardLayout.setOnClickListener {
            val intent = Intent(context, RestaurantActivity::class.java)
            intent.putExtra("restaurantID", restaurants[position].id)
            context?.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return restaurants.size
    }

    private fun setTagParams(tag: String): TextView {
        val textView = TextView(context, null, 0, R.style.tag_text)
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
