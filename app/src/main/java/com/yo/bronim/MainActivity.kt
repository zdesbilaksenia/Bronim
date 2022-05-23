package com.yo.bronim

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts.RequestPermission
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentContainerView
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.yandex.mapkit.MapKitFactory

class MainActivity : AppCompatActivity() {
    private val mPermissionResult = registerForActivityResult(RequestPermission()) { }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val ai: ApplicationInfo = applicationContext.packageManager
            .getApplicationInfo(applicationContext.packageName, PackageManager.GET_META_DATA)
        MapKitFactory.setApiKey(ai.metaData["mapKey"].toString())
        MapKitFactory.initialize(this)
        mPermissionResult.launch(Manifest.permission.ACCESS_FINE_LOCATION)

        val navController =
            findViewById<FragmentContainerView>(R.id.nav_host_fragment_container)
                .getFragment<NavHostFragment>().navController

        findViewById<BottomNavigationView>(R.id.main_navigation).setupWithNavController(
            navController
        )
    }

    companion object {
        fun newInstance(context: Context?) {
            Intent(context, MainActivity::class.java)
        }
    }
}
