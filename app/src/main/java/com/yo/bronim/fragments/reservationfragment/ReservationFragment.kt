package com.yo.bronim.fragments.reservationfragment

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.ContextThemeWrapper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.cardview.widget.CardView
import androidx.constraintlayout.helper.widget.Flow
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.ViewCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.yo.bronim.AuthorizationActivity
import com.yo.bronim.R
import com.yo.bronim.models.PostReservation
import com.yo.bronim.states.ReservationPageState
import com.yo.bronim.viewmodels.ReservationPageViewModel
import java.util.Calendar
import java.util.GregorianCalendar
import kotlin.collections.ArrayList

const val REST_START = 0
const val REST_FINISH = 47

class ReservationFragment : Fragment() {
    private var progressBar: ProgressBar? = null
    private var spinner: AutoCompleteTextView? = null
    private var dateRecycler: RecyclerView? = null
    private var tableRecycler: RecyclerView? = null
    private var calendar = Calendar.getInstance()
    private var weekdays: Array<String>? = null
    private val currentMonth = calendar.get(Calendar.MONTH)
    private val currentDay = calendar.get(Calendar.DAY_OF_MONTH)

    var bundle: Bundle? = Bundle()

    private var chosenDay: Pair<String, Int>? = null
    private var chosenMonth: Int = currentMonth
    private var numOfGuests: Int = 1
    private var chosenTable: Int? = null
    private var chosenTime: MutableList<Int> = ArrayList()
    private var restId: Int? = null
    private var tables: MutableList<Int> = ArrayList()
    private var times: MutableMap<Int, List<Int>> = mutableMapOf()
    private var restStart: Int = REST_START
    private var restFinish: Int = REST_FINISH

    private var tableCard: CardView? = null
    private var timeCard: CardView? = null
    private var timeCardConstraint: ConstraintLayout? = null
    private var timeFlow: Flow? = null
    private var timeCells: MutableList<View>? = null

    private val reservationPageViewModel = ReservationPageViewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_reservation, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        progressBar = view.findViewById(R.id.progress_bar)

        observeAvailableTablesAndTime()

        restId = bundle?.getInt("id")
        restStart = bundle?.getInt("start")!!
        restFinish = bundle?.getInt("end")!!

        weekdays = activity?.resources?.getStringArray(R.array.weekdays)

        tableCard = view.findViewById(R.id.table_card)
        timeCard = view.findViewById(R.id.time_card)
        timeCardConstraint = view.findViewById(R.id.time_card_constraint)
        timeFlow = view.findViewById(R.id.time_flow)

        spinner = view.findViewById(R.id.month_spinner)

        dateRecycler = view.findViewById(R.id.date_recycler)
        dateRecycler?.layoutManager =
            LinearLayoutManager(activity, RecyclerView.HORIZONTAL, false)

        tableRecycler = view.findViewById(R.id.table_recycler)
        tableRecycler?.layoutManager =
            LinearLayoutManager(activity, RecyclerView.HORIZONTAL, false)

        setSpinnerAdapter()

        spinner?.onItemClickListener =
            AdapterView.OnItemClickListener { parent, view, position, id ->
                val month = position + currentMonth
                calendar = if (currentMonth == month) {
                    GregorianCalendar(Calendar.YEAR, currentMonth, currentDay)
                } else {
                    GregorianCalendar(Calendar.YEAR, month, 1)
                }
                chosenMonth = month

                setDateRecycler(
                    calendar.getActualMaximum(Calendar.DATE),
                    calendar.get(Calendar.DAY_OF_MONTH)
                )
            }

        val minus = view.findViewById<TextView>(R.id.minus_guests)
        val plus = view.findViewById<TextView>(R.id.plus_guests)
        val guests = view.findViewById<TextView>(R.id.num_of_guests)

        minus.setOnClickListener {
            if (numOfGuests != 1) {
                numOfGuests--
                guests.text = numOfGuests.toString()
                if (chosenDay != null) {
                    getAvailableTablesAndTime()
                }
            }
        }

        plus.setOnClickListener {
            numOfGuests++
            guests.text = numOfGuests.toString()
            if (chosenDay != null) {
                getAvailableTablesAndTime()
            }
        }

        val backButton = view.findViewById<Button>(R.id.reservation_back_btn)
        backButton.setOnClickListener {
            activity?.finish()
        }

        val okButton = view.findViewById<Button>(R.id.reservation_ok_btn)
        okButton.setOnClickListener {
            if (chosenTable != null && chosenDay != null && chosenTime.size > 0 && restId != null) {
                val user = AuthorizationActivity.getFBUser()
                if (user != null) {
                    reservationPageViewModel.sendReservationInfo(
                        PostReservation(
                            chosenTable.toString(),
                            user.uid,
                            convertChosenDate(),
                            numOfGuests,
                            chosenTime,
                            null
                        ),
                        restId!!,
                        chosenTable!!
                    )
                }
            }
            activity?.finish()
        }
    }

    private fun setSpinnerAdapter() {
        val monthArray: List<String> = resources.getStringArray(R.array.months).toList()
        val monthAdapter = ArrayAdapter(
            activity as Context,
            R.layout.simple_spinner_item,
            monthArray.slice(currentMonth until monthArray.size)
        )

        spinner?.setAdapter(monthAdapter)
        spinner?.setDropDownBackgroundDrawable(
            ResourcesCompat.getDrawable(
                resources,
                R.drawable.reservation_item_bckgrnd,
                null
            )
        )
        spinner?.setText(monthAdapter.getItem(0), false)

        setDateRecycler(
            calendar.getActualMaximum(Calendar.DATE),
            calendar.get(Calendar.DAY_OF_MONTH)
        )
    }

    private fun setDateRecycler(numOfDays: Int, currentDay: Int) {
        val dates: MutableList<Pair<String, Int>> = ArrayList()

        for (i in currentDay..numOfDays) {
            calendar.set(Calendar.DAY_OF_MONTH, i)
            dates.add(Pair(weekdays!![calendar.get(Calendar.DAY_OF_WEEK) - 1], i))
        }

        dateRecycler?.adapter = DateAdapter(dates) { date ->
            chosenDay = date
            tableCard?.visibility = View.VISIBLE

            getAvailableTablesAndTime()
        }
    }

    private fun setTableRecycler() {
        chosenTime = ArrayList()
        chosenTable = null

        tableRecycler?.adapter = TableAdapter(tables) { table ->
            chosenTable = table
            timeCard?.visibility = View.VISIBLE

            setTimeCells()
        }
    }

    private fun setTimeCells() {
        val times = times[chosenTable] as List
        timeCells?.forEach {
            timeCardConstraint?.removeView(it)
        }
        timeCells = ArrayList()
        timeFlow?.referencedIds = intArrayOf()
        for (i in restStart..restFinish) {
            val textView = TextView(ContextThemeWrapper(activity, R.style.time))
            textView.id = ViewCompat.generateViewId()
            textView.setBackgroundResource(R.drawable.reservation_item_bckgrnd)
            textView.text =
                "${(i / 2).toString().padStart(2, '0')}:${(i % 2 * 30).toString().padStart(2, '0')}"
            textView.width = 240

            if (times.contains(i)) {
                textView.setBackgroundResource(R.drawable.time_picked_bckgrnd)
                textView.isClickable = false
                textView.setTextColor(
                    ResourcesCompat.getColor(
                        resources,
                        R.color.main_light_grey,
                        null
                    )
                )
            } else {
                textView.setOnClickListener {
                    if (!chosenTime.contains(i)) {
                        textView.setBackgroundResource(R.drawable.reservation_item_chosen)
                        chosenTime.add(i)
                    } else {
                        textView.setBackgroundResource(R.drawable.reservation_item_bckgrnd)
                        chosenTime.remove(i)
                    }
                }
            }

            timeFlow?.referencedIds = timeFlow?.referencedIds?.plus(textView.id)

            timeCells?.add(textView)
            timeCardConstraint?.addView(textView)
        }
    }

    private fun observeAvailableTablesAndTime() {
        reservationPageViewModel.reservationsState.observe(viewLifecycleOwner) { state ->
            when (state) {
                // is Pending
                is ReservationPageState.Success -> {
                    progressBar?.visibility = View.GONE
                    if (state.result.isNotEmpty()) {
                        state.result.forEach {
                            tableCard?.visibility = View.VISIBLE
                            tables.add(it.table)
                            times[it.table] = it.times
                            setTableRecycler()
                        }
                    }
                }
                is ReservationPageState.Error -> Log.e("ERR", state.error.toString())
            }
        }
    }

    private fun getAvailableTablesAndTime() {
        tableCard?.visibility = View.GONE
        timeCard?.visibility = View.GONE
        times = mutableMapOf()
        tables = mutableListOf()
        reservationPageViewModel.getAvailableTablesAndTime(
            restId!!,
            convertChosenDate(),
            numOfGuests
        )
    }

    private fun convertChosenDate(): String {
        return "${calendar.get(Calendar.YEAR)}-${
        (chosenMonth + 1).toString().padStart(2, '0')
        }-${chosenDay?.second.toString().padStart(2, '0')}"
    }

    companion object {
        fun newInstance(start: Int, end: Int, id: Int) = ReservationFragment().apply {
            bundle?.putInt("start", start)
            bundle?.putInt("end", end)
            bundle?.putInt("id", id)
            arguments = bundle?.apply {
                putInt("start", start)
                bundle?.putInt("end", end)
                bundle?.putInt("id", id)
            }
        }
    }
}
