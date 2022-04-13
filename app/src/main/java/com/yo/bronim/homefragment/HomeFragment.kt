package com.yo.bronim.homefragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.yo.bronim.R
import com.yo.bronim.RegistrationActivity
import com.yo.bronim.homefragment.adapter.MainAdapter

class HomeFragment : Fragment() {
    private var recycler: RecyclerView? = null
    private var layoutManager: RecyclerView.LayoutManager? = null
    private var adapter: RecyclerView.Adapter<MainAdapter.MainViewHolder>? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        val recyclerView: RecyclerView = view.findViewById(R.id.main_recycler)
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = MainAdapter()

        val profileImageView = view.findViewById<ImageView>(R.id.home__profile_image)
        profileImageView.setOnClickListener {
            val intent = Intent(activity, RegistrationActivity::class.java)
            startActivity(intent)
        }

        return view
    }

    companion object {
        fun newInstance() = HomeFragment()
    }
}
