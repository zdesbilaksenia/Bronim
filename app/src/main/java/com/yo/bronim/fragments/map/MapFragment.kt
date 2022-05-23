package com.yo.bronim.fragments.map

import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.content.Context
import android.content.Intent
import android.graphics.PointF
import android.os.Bundle
import android.text.TextUtils
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.cardview.widget.CardView
import androidx.core.content.PermissionChecker.PERMISSION_GRANTED
import androidx.core.content.PermissionChecker.checkSelfPermission
import androidx.fragment.app.Fragment
import com.blure.complexview.ComplexView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.yandex.mapkit.Animation
import com.yandex.mapkit.MapKitFactory
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.layers.GeoObjectTapListener
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
import com.yo.bronim.RestaurantActivity
import com.yo.bronim.fragments.home.adapter.TAG_MARGIN
import com.yo.bronim.models.Restaurant
import com.yo.bronim.states.MapPageState
import com.yo.bronim.viewmodels.MapPageViewModel

const val MOSCOW_LAT = 55.751244
const val MOSCOW_LNG = 37.618423

const val TEST_LAT = 55.765990
const val TEST_LNG = 37.684560

class MapFragment : Fragment(), UserLocationObjectListener, CameraListener {
    private var mapPageViewModel = MapPageViewModel()

    private var mapView: MapView? = null
    private var fab: CardView? = null
    private var userLocationLayer: UserLocationLayer? = null

    private var container: ViewGroup? = null

    private var followUserLocation = false

    private val restCards = mutableMapOf<View, Boolean>()
    private val markers = mutableMapOf<View, PlacemarkMapObject>()
    private val viewGroups = mutableMapOf<PlacemarkMapObject, View>()
    private var restaurants = mutableListOf<Restaurant>()
    private val markerRest = mutableMapOf<PlacemarkMapObject, Restaurant>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        this.container = container
        return inflater.inflate(R.layout.fragment_map, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mapView = view.findViewById(R.id.mapview)
        fab = view.findViewById(R.id.geo_fab)

        observeRestaurants()

        mapPageViewModel.getRestaurants()

        checkPermission()
        fab?.setOnClickListener {
            cameraUserPosition()
            followUserLocation = true
        }

        mapView?.map?.addTapListener(mapTapListener)
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

    override fun onObjectRemoved(p0: UserLocationView) {}

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
        restaurants.forEach {
            val viewGroup = layoutInflater.inflate(R.layout.marker_layout, mapView, false)

            val mark =
                mapView?.map?.mapObjects?.addPlacemark(
                    Point(it.lat + 0.01, it.lng + 0.01),
                    ViewProvider(viewGroup),
                    IconStyle().setAnchor(PointF(0.5f, 1f))
                )

            viewGroups[mark as PlacemarkMapObject] = viewGroup
            markerRest[mark] = it

            (mark as MapObject).addTapListener(markerTapListener)
        }
    }

    private fun invalidateCards() {
        restCards.forEach {
            val card = it.key.findViewById<ComplexView>(R.id.marker_card)
            card.visibility = View.GONE
            markers[it.key]?.setView(ViewProvider(it.key))
            markers[it.key]?.setIconStyle(IconStyle().setAnchor(PointF(0.5f, 1f)))
            markers[it.key]?.removeTapListener(cardTapListener)
        }
    }

    private val markerTapListener =
        MapObjectTapListener { mapObject, _ ->
            invalidateCards()
            val viewGroup = viewGroups[mapObject as PlacemarkMapObject]
            fillCard(viewGroup, markerRest[mapObject])
            val restCard = viewGroup?.findViewById<ComplexView>(R.id.marker_card)
            restCard?.visibility = View.VISIBLE
            val image = viewGroup?.findViewById<ImageView>(R.id.marker_rest_img)

            Glide.with(this).load(markerRest[mapObject]?.img).diskCacheStrategy(
                DiskCacheStrategy.ALL
            )
                .into(image!!)

            mapObject.setView(ViewProvider(viewGroup))
            mapObject.setIconStyle(IconStyle().setAnchor(PointF(0.5f, 1f)))
            restCards[viewGroup] = true
            markers[viewGroup] = mapObject

            mapObject.addTapListener(cardTapListener)
            true
        }

    private fun fillCard(viewGroup: View?, restaurant: Restaurant?) {
        val name = viewGroup?.findViewById<TextView>(R.id.marker_rest_name)
        val address = viewGroup?.findViewById<TextView>(R.id.marker_rest_address)
        val tagsContainer = viewGroup?.findViewById<LinearLayout>(R.id.marker_rest_tags)
        val rating = viewGroup?.findViewById<RatingBar>(R.id.marker_rest_rating)

        name?.text = restaurant?.name
        address?.text = restaurant?.address
        restaurant?.tags?.forEach {
            val textView = setTagParams(it)
            tagsContainer?.addView(textView)
        }
        rating?.rating = restaurant!!.rating
    }

    private val mapTapListener = GeoObjectTapListener {
        invalidateCards()
        true
    }

    private val cardTapListener = MapObjectTapListener { mapObject, _ ->
        val intent = Intent(context, RestaurantActivity::class.java)
        intent.putExtra("restaurantID", markerRest[mapObject as PlacemarkMapObject]?.id)
        context?.startActivity(intent)
        true
    }

    private fun observeRestaurants() {
        mapPageViewModel.restaurantsState.observe(viewLifecycleOwner) { state ->
            when (state) {
                is MapPageState.Success -> {
                    restaurants = state.result.toMutableList()
                    addMarks()
                }
            }
        }
    }

    private fun setTagParams(tag: String): TextView {
        val textView = TextView(context, null, 0, R.style.tag_text)
        textView.setBackgroundResource(R.drawable.tag_bckgrnd)
        textView.text = tag
        textView.ellipsize = TextUtils.TruncateAt.END
        textView.maxLines = 1
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12f)

        val marginParams = ViewGroup.MarginLayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        marginParams.marginEnd = TAG_MARGIN
        textView.layoutParams = marginParams

        return textView
    }
}
