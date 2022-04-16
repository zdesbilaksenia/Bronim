package com.yo.bronim.homefragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.yo.bronim.R
import com.yo.bronim.AuthorizationActivity
import com.yo.bronim.contracts.AuthorizationContract
import com.yo.bronim.homefragment.adapter.MainAdapter

class HomeFragment : Fragment() {
    private var recycler: RecyclerView? = null
    private var layoutManager: RecyclerView.LayoutManager? = null
    private var adapter: RecyclerView.Adapter<MainAdapter.MainViewHolder>? = null

    private val textViewName by lazy {
        view?.findViewById<TextView>(R.id.home__name)
    }

    private val authorize = registerForActivityResult(AuthorizationContract()) { email ->
        textViewName?.text = email
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        val recyclerView: RecyclerView = view.findViewById(R.id.main_recycler)
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = MainAdapter()
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val profileImageView = view.findViewById<ImageView>(R.id.home__profile_image)
        profileImageView.setOnClickListener {
            authorize.launch(Unit)
        }
    }



    companion object {
        fun newInstance() = HomeFragment()
    }
}


