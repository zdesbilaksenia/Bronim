package com.yo.bronim.fragments.map

import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.content.Context
import android.graphics.PointF
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.core.content.PermissionChecker.PERMISSION_GRANTED
import androidx.core.content.PermissionChecker.checkSelfPermission
import androidx.fragment.app.Fragment
import com.yandex.mapkit.Animation
import com.yandex.mapkit.MapKitFactory
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.layers.ObjectEvent
import com.yandex.mapkit.map.*
import com.yandex.mapkit.map.Map
import com.yandex.mapkit.mapview.MapView
import com.yandex.mapkit.user_location.UserLocationLayer
import com.yandex.mapkit.user_location.UserLocationObjectListener
import com.yandex.mapkit.user_location.UserLocationView
import com.yandex.runtime.image.ImageProvider
import com.yandex.runtime.ui_view.ViewProvider
import com.yo.bronim.R

const val MOSCOW_LAT = 55.751244
const val MOSCOW_LNG = 37.618423

const val TEST_LAT = 55.765990
const val TEST_LNG = 37.684560

class MapFragment : Fragment(), UserLocationObjectListener, CameraListener {
    private var mapView: MapView? = null
    private var fab: CardView? = null
    private var userLocationLayer: UserLocationLayer? = null
    private var userLocationView: UserLocationView? = null

    private var followUserLocation = false

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
        fab = view.findViewById(R.id.geo_fab)

        checkPermission()
        fab?.setOnClickListener {
            cameraUserPosition()
            followUserLocation = true
        }

        addMarks()
    }

    override fun onStart() {
        super.onStart()
        mapView?.onStart()
        MapKitFactory.getInstance().onStart()
    }

    override fun onStop() {
        super.onStop()
        mapView?.onStop()
        MapKitFactory.getInstance().onStop()
    }

    private fun checkPermission() {
        val permissionFineLocation = checkSelfPermission(activity as Context, ACCESS_FINE_LOCATION)
        if (permissionFineLocation == PERMISSION_GRANTED) {
            prepareMap()
        } else {
            cameraDefaultPosition()
        }
    }

    private fun prepareMap() {
        mapView?.map?.addCameraListener(this)

        userLocationLayer = MapKitFactory.getInstance().createUserLocationLayer(mapView!!.mapWindow)

        userLocationLayer?.isVisible = true
        userLocationLayer?.isHeadingEnabled = false
        userLocationLayer?.setObjectListener(this)
    }

    private fun cameraUserPosition() {
        if (userLocationLayer?.cameraPosition() != null) {
            val location = userLocationLayer?.cameraPosition()!!.target
            mapView?.map?.move(
                CameraPosition(location, 16f, 0f, 0f),
                Animation(Animation.Type.SMOOTH, 1f),
                null
            )
        } else {
            Toast.makeText(activity, R.string.geo_error, Toast.LENGTH_SHORT).show()
        }
    }

    private fun cameraDefaultPosition() {
        Toast.makeText(activity, R.string.geo_error, Toast.LENGTH_SHORT).show()
        mapView?.map?.move(CameraPosition(Point(MOSCOW_LAT, MOSCOW_LNG), 16f, 0f, 0f))
    }

    companion object {
        fun newInstance() = MapFragment()
    }

    override fun onObjectAdded(userLocationView: UserLocationView) {
        val pinIcon: CompositeIcon = userLocationView.pin.useCompositeIcon()

        pinIcon.setIcon(
            "pin",
            ImageProvider.fromResource(activity, R.drawable.search_result),
            IconStyle().setAnchor(PointF(0.5f, 0.5f))
                .setRotationType(RotationType.ROTATE)
                .setZIndex(1f)
                .setScale(0.5f)
        )

        followUserLocation = true
    }

    override fun onObjectRemoved(p0: UserLocationView) {
    }

    override fun onObjectUpdated(p0: UserLocationView, p1: ObjectEvent) {
        if (followUserLocation) cameraUserPosition()
    }

    private fun setAnchor() {
        userLocationLayer?.setAnchor(
            PointF((mapView!!.width * 0.5).toFloat(), (mapView!!.height * 0.5).toFloat()),
            PointF((mapView!!.width * 0.5).toFloat(), (mapView!!.height * 0.83).toFloat())
        )
        followUserLocation = false
    }

    override fun onCameraPositionChanged(
        p0: Map,
        p1: CameraPosition,
        p2: CameraUpdateReason,
        finished: Boolean
    ) {
        if (finished) {
            if (followUserLocation) {
                setAnchor()
            }
        } else {
            if (!followUserLocation) {
                userLocationLayer?.resetAnchor()
            }
        }
    }

    private fun addMarks() {
        val view = View(requireContext()).apply {
            background = requireContext().getDrawable(R.drawable.ic_map_mark)
        }
        val mark =
            mapView?.map?.mapObjects?.addPlacemark(Point(TEST_LAT, TEST_LNG), ViewProvider(view))
        mark?.addTapListener { mapObject, point ->
            Toast.makeText(activity, "Нажато", Toast.LENGTH_SHORT).show()
            true
        }
    }
}
