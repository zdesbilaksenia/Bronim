package com.yo.bronim

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts.RequestPermission
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentContainerView
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.gms.common.api.internal.LifecycleActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.yandex.mapkit.MapKitFactory
import com.yo.bronim.contracts.AuthorizationContract
import com.yo.bronim.fragments.home.HomeFragment
import com.yo.bronim.states.AuthorizationPageState
import com.yo.bronim.viewmodels.AuthorizationPageViewModel
import java.util.*

class MainActivity : AppCompatActivity() {
    private val mPermissionResult = registerForActivityResult(RequestPermission()) { }

    private var homePageAuthorizationViewModel = AuthorizationPageViewModel()

    private val textViewName by lazy {
        findViewById<TextView>(R.id.home__name)
    }

    private val profileImageView by lazy {
        findViewById<ImageView>(R.id.home__profile_image)
    }

    private val authorize = registerForActivityResult(AuthorizationContract()) { user ->
        if (user != null) {
            textViewName?.text = user.name
        }
    }

    private val mPermissionResult = registerForActivityResult(RequestPermission()) { }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val ai: ApplicationInfo = applicationContext.packageManager
            .getApplicationInfo(applicationContext.packageName, PackageManager.GET_META_DATA)
        MapKitFactory.setApiKey(ai.metaData["mapKey"].toString())
        MapKitFactory.initialize(this)
        mPermissionResult.launch(Manifest.permission.ACCESS_FINE_LOCATION)

        if (savedInstanceState != null) {
            textViewName?.text = savedInstanceState.getString(MainActivity.UserNameVariable)
        }

        mPermissionResult.launch(Manifest.permission.ACCESS_FINE_LOCATION)

        val ai: ApplicationInfo = applicationContext.packageManager
            .getApplicationInfo(applicationContext.packageName, PackageManager.GET_META_DATA)

        MapKitFactory.setApiKey(ai.metaData["mapKey"].toString())
        MapKitFactory.initialize(this)

        val navController =
            findViewById<FragmentContainerView>(R.id.nav_host_fragment_container)
                .getFragment<NavHostFragment>().navController

        findViewById<BottomNavigationView>(R.id.main_navigation).setupWithNavController(
            navController
        )
    }

    override fun onStart() {
        super.onStart()
        homePageAuthorizationViewModel = AuthorizationPageViewModel()

        observeIsAuthorized()

        homePageAuthorizationViewModel.isAuthorized()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        val username = textViewName?.text.toString()
        outState.putString(MainActivity.UserNameVariable, username)
    }

    private fun observeIsAuthorized() {
        homePageAuthorizationViewModel.isAuthorizedState.observe(this){ state ->
            when (state) {
                is AuthorizationPageState.Success -> {
                    textViewName.text = ", ${state.user?.name}"
                    profileImageView?.setImageDrawable(
                        resources.getDrawable(R.drawable.ic_profile)
                    )
                    profileImageView?.setOnClickListener {
                        val intent = Intent(this, ProfileActivity::class.java)
                        startActivity(intent)
                    }
                }
                is AuthorizationPageState.Error -> {
                    profileImageView?.setImageDrawable(
                        resources.getDrawable(R.drawable.ic_login)
                    )
                    profileImageView?.setOnClickListener {
                        authorize.launch(Unit)
                    }
                    textViewName.text = ""
                }
            }
        }
    }

    companion object {
        fun newInstance(context: Context?) {
            Intent(context, MainActivity::class.java)
        }
        var UserNameVariable = "USERNAME"
    }
}
