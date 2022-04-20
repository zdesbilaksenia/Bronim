package com.yo.bronim.fragments.reservationfragment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.yo.bronim.R

class TableAdapter(
    private val tables: MutableList<Int>,
    private val callback: (table: Int) -> Unit
) :
    RecyclerView.Adapter<TableAdapter.TableViewHolder>() {

    private var previousChosen: ConstraintLayout? = null

    inner class TableViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tableNum: TextView = itemView.findViewById(R.id.table_num)
        val tableCell: ConstraintLayout = itemView.findViewById(R.id.table_cell)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TableViewHolder {
        val inflate =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.table_item, parent, false)
        return TableViewHolder(inflate)
    }

    override fun onBindViewHolder(holder: TableViewHolder, position: Int) {
        holder.tableNum.text = "№ ${tables[position]}"

        holder.tableCell.setOnClickListener {
            previousChosen?.setBackgroundResource(R.drawable.reservation_item_bckgrnd)
            holder.tableCell.setBackgroundResource(R.drawable.reservation_item_chosen)
            callback(tables[position])
            previousChosen = holder.tableCell
        }
    }

    override fun getItemCount(): Int {
        return tables.size
    }
}
