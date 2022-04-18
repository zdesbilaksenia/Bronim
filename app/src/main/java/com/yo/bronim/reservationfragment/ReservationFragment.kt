package com.yo.bronim.reservationfragment

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.yo.bronim.R
import com.yo.bronim.homefragment.adapter.MainAdapter
import java.util.*


class ReservationFragment : Fragment() {
    private var spinner: Spinner? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d("Started", "yes")
        return inflater.inflate(R.layout.fragment_reservation, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        spinner = view.findViewById(R.id.month_spinner)
        val monthAdapter = ArrayAdapter.createFromResource(
            activity as Context, R.array.months,
            android.R.layout.simple_spinner_item
        );
        monthAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown)
        spinner?.adapter = monthAdapter

        spinner?.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                val recycler = view.findViewById<RecyclerView>(R.id.date_recycler)
                recycler?.layoutManager =
                    LinearLayoutManager(activity, RecyclerView.HORIZONTAL, false)
                recycler?.adapter = DateAdapter()
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                val calendar = Calendar.getInstance()
                calendar.firstDayOfWeek
            }
        }
    }
}