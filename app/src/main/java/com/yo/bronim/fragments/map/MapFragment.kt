package com.yo.bronim.fragments.map

import android.Manifest.permission.ACCESS_COARSE_LOCATION
import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.content.Context
import android.graphics.Color
import android.graphics.PointF
import android.os.Bundle
import android.util.Log
import com.yandex.mapkit.logo.HorizontalAlignment.LEFT
import com.yandex.mapkit.logo.VerticalAlignment.BOTTOM
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.PermissionChecker.PERMISSION_GRANTED
import androidx.core.content.PermissionChecker.checkSelfPermission
import androidx.fragment.app.Fragment
import com.yandex.mapkit.Animation
import com.yandex.mapkit.MapKitFactory
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.layers.ObjectEvent
import com.yandex.mapkit.logo.Alignment
import com.yandex.mapkit.map.*
import com.yandex.mapkit.map.Map
import com.yandex.mapkit.mapview.MapView
import com.yandex.mapkit.user_location.UserLocationLayer
import com.yandex.mapkit.user_location.UserLocationObjectListener
import com.yandex.mapkit.user_location.UserLocationView
import com.yandex.runtime.image.ImageProvider
import com.yo.bronim.R


class MapFragment : Fragment(), UserLocationObjectListener, CameraListener {
    private var mapView: MapView? = null
    private var userLocationLayer: UserLocationLayer? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_map, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mapView = view.findViewById(R.id.mapview)


        checkPermission()
        val mapLogoAlignment = Alignment(LEFT, BOTTOM)
        mapView?.map?.logo?.setAlignment(mapLogoAlignment)
        user_location_fab.setImageResource(R.drawable.ic_my_location_black_24dp)
        mapView?.map?.move(
            CameraPosition(Point(55.751574, 37.573856), 11.0f, 0.0f, 0.0f),
            Animation(Animation.Type.SMOOTH, 0f),
            null
        )
    }

    override fun onStart() {
        super.onStart()
        mapView?.onStart();
        MapKitFactory.getInstance().onStart();
    }

    override fun onStop() {
        super.onStop()
        mapView?.onStop();
        MapKitFactory.getInstance().onStop();
    }

    private fun checkPermission() {
        val permissionFineLocation = checkSelfPermission(activity as Context, ACCESS_FINE_LOCATION)
        val permissionCoarseLocation =
            checkSelfPermission(activity as Context, ACCESS_COARSE_LOCATION)
        if (permissionFineLocation == PERMISSION_GRANTED || permissionCoarseLocation == PERMISSION_GRANTED) {
            prepareMap()
            Log.d("PERMISSION", "Granted ${permissionFineLocation}, ${permissionCoarseLocation}")
        } else {
            Toast.makeText(
                activity, "Не удалось получить данные о местоположении", Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun prepareMap() {
        userLocationLayer = mapView?.mapWindow?.let {
            MapKitFactory.getInstance().createUserLocationLayer(
                it
            )
        }
        userLocationLayer?.isVisible = true
        userLocationLayer?.isHeadingEnabled = true
        userLocationLayer?.setObjectListener(this)

        mapView?.map?.addCameraListener(this)

//        cameraUserPosition()

//        permissionLocation = true
    }


    companion object {
        fun newInstance() = MapFragment()
        const val requestPermissionLocation = 1
    }

    override fun onObjectAdded(userLocationView: UserLocationView) {
        userLocationLayer!!.setAnchor(
            PointF((mapView!!.width * 0.5).toFloat(), (mapView!!.height * 0.5).toFloat()),
            PointF((mapView!!.width * 0.5).toFloat(), (mapView!!.height * 0.83).toFloat())
        )

//        userLocationView.arrow.setIcon(
//            ImageProvider.fromResource(
//                activity, R.drawable.user_arrow
//            )
//        )

        val pinIcon = userLocationView.pin.useCompositeIcon()

//        pinIcon.setIcon(
//            "icon",
//            ImageProvider.fromResource(activity, R.drawable.icon),
//            IconStyle().setAnchor(PointF(0f, 0f))
//                .setRotationType(RotationType.ROTATE)
//                .setZIndex(0f)
//                .setScale(1f)
//        )
//
//        pinIcon.setIcon(
//            "pin",
//            ImageProvider.fromResource(activity, R.drawable.search_result),
//            IconStyle().setAnchor(PointF(0.5f, 0.5f))
//                .setRotationType(RotationType.ROTATE)
//                .setZIndex(1f)
//                .setScale(0.5f)
//        )

//        userLocationView.accuracyCircle.fillColor = Color.BLUE and -0x66000001
//        userLocationView.pin.setIcon(fromResource(activity, R.drawable.ic_fav))
//        userLocationView.arrow.setIcon(fromResource(activity, R.drawable.ic_fav))
//        userLocationView.accuracyCircle.fillColor = BLUE
    }

    override fun onObjectRemoved(p0: UserLocationView) {
    }

    override fun onObjectUpdated(p0: UserLocationView, p1: ObjectEvent) {
    }

    override fun onCameraPositionChanged(
        p0: Map,
        p1: CameraPosition,
        p2: CameraUpdateReason,
        finished: Boolean
    ) {
        userLocationLayer?.setAnchor(
            PointF((mapView!!.width * 0.5).toFloat(), (mapView!!.height * 0.5).toFloat()),
            PointF((mapView!!.width * 0.5).toFloat(), (mapView!!.height * 0.83).toFloat())
        )

//        user_location_fab.setImageResource(R.drawable.ic_my_location_black_24dp)
    }
}
