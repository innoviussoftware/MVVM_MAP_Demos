package com.example.mvvm_map_demos.ui.map

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.mvvm_map_demos.R
import java.util.*

import android.Manifest
import android.app.Activity
import android.content.IntentSender
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import android.location.LocationManager
import android.net.Uri
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.location.LocationManagerCompat
import com.example.mvvm_map_demos.utils.Applog
import com.example.mvvm_map_demos.utils.GlobalValues
import com.github.florent37.runtimepermission.kotlin.askPermission
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.*
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.fragment_map_home.*

import java.io.IOException
import java.util.ArrayList

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class MapHomeFragment : Fragment(), OnMapReadyCallback {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private var mContext: Activity? = null
     lateinit var mMap: GoogleMap
     lateinit var mFusedLocationClient: FusedLocationProviderClient
    //    private lateinit var myCurrentLocation: Location
     var isCurrentLocation = true
    //    private var isSearch = false
    // val arrMapStationList: ArrayList<Data> = arrayListOf()
     var latitude = "0.0"
     var longitude = "0.0"
     var onCameraIdleListener: GoogleMap.OnCameraIdleListener? = null


    companion object {
        @JvmStatic
        fun newInstance() = MapHomeFragment()

        var mapFragment: SupportMapFragment? = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is Activity) {
            mContext = context
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_map_home, container, false)
        mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(this)
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())

        configureCameraIdle()
    }

    private fun configureCameraIdle() {
        onCameraIdleListener = GoogleMap.OnCameraIdleListener {
            val latLng = mMap.cameraPosition.target
            val geocoder = Geocoder(mContext!!)
            try {
                val addressList = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1)
                tvCurrentLocation.setText("Location--:" + latLng.latitude + "long-->" + latLng.longitude)
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }

    override fun onMapReady(googleMap: GoogleMap?) {
        mMap = googleMap!!
        mMap.uiSettings.isMapToolbarEnabled = false
        isCurrentLocation = true
        checkLocationPermission()
        mMap.setOnCameraIdleListener(onCameraIdleListener)
    }

    private fun checkLocationPermission() {
        askPermission(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
        ) {
            openPhoneLocationSettingDialog()
        }.onDeclined { e ->
            if (e.hasDenied()) {
                AlertDialog.Builder(requireContext())
                    .setMessage(getString(R.string.grant_permission))
                    .setPositiveButton(resources.getString(R.string.yes)) { dialog, which ->
                        e.askAgain()
                    } //ask again
                    .setNegativeButton(resources.getString(R.string.no)) { dialog, which ->
                        dialog.dismiss()
                    }
                    .show()
            }

            if (e.hasForeverDenied()) {
                e.goToSettings()
            }
        }
    }

    private fun openPhoneLocationSettingDialog() {
        val builder = LocationSettingsRequest.Builder()
            .addLocationRequest(LocationRequest().setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY))
            .setAlwaysShow(true)

        val settingsClient = LocationServices.getSettingsClient(requireContext())

        val task = settingsClient!!.checkLocationSettings(builder.build())
        task.addOnCompleteListener {
            try {
                val response = it.getResult(ApiException::class.java)
                //Success
                getCurrentLocation()
            } catch (exception: ApiException) {
                when (exception.statusCode) {
                    LocationSettingsStatusCodes.RESOLUTION_REQUIRED -> {
                        // Location settings are not satisfied. But could be fixed by showing the
                        // user a dialog.
                        try {
                            // Cast to a resolvable exception.
                            val resolvable = exception as ResolvableApiException
                            // Show the dialog by calling startResolutionForResult(),
                            // and check the result in onActivityResult().

                            this.startIntentSenderForResult(
                                resolvable.resolution.intentSender,
                                GlobalValues.RC_LOCATION_ENABLE,
                                null, 0, 0, 0, null
                            )
                        } catch (e: IntentSender.SendIntentException) {
                            // Ignore the error.
                        } catch (e: ClassCastException) {
                            // Ignore, should be an impossible error.
                        }
                    }
                    LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE -> {
                        // Location settings are not satisfied. However, we have no way to fix the
                        // settings so we won't show the dialog.
                    }
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            GlobalValues.RC_LOCATION_ENABLE -> {
                if (resultCode == Activity.RESULT_OK) {
                    Applog.E("Location is enabled by user 11")
                    getCurrentLocation()
                } else {
                    Applog.E("Location enable request is cancelled by user 11")
                }
                val lm =
                    requireContext().getSystemService(AppCompatActivity.LOCATION_SERVICE) as LocationManager
                if (LocationManagerCompat.isLocationEnabled(lm)) {
                    Applog.E("Location is enabled by user 22")
//                    getCurrentLocation()
                } else {
                    Applog.E("Location enable request is cancelled by user 22")
                }
            }
            GlobalValues.PLACE_AUTOCOMPLETE_REQUEST_CODE -> {
                when (resultCode) {
                    Activity.RESULT_OK -> {
                       // val place = Autocomplete.getPlaceFromIntent(data!!)
                    }
                   /* AutocompleteActivity.RESULT_ERROR -> {
                        val status = Autocomplete.getStatusFromIntent(data!!)
                        Applog.E("Add" + status.statusMessage)
                    }*/
                    Activity.RESULT_CANCELED -> {
                        // The user canceled the operation.
                    }
                }
            }
        }
    }

    private fun getCurrentLocation() {
        if (ActivityCompat.checkSelfPermission(
                mContext!!,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                mContext!!,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return
        }
        mFusedLocationClient.lastLocation!!.addOnCompleteListener(requireActivity()) { task ->
            val location: Location? = task.result
            if (location == null) {
                requestNewLocationData()
            } else {
                Applog.E("Latitude--11-->" + location.latitude.toString() + " Longitude--" + location.longitude.toString())
                latitude = location.latitude.toString()
                longitude = location.longitude.toString()
                moveToMyCurrentLocation(location)
            }
        }
    }

    private fun requestNewLocationData() {
        val mLocationRequest = LocationRequest()
        mLocationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        mLocationRequest.interval = 0
        mLocationRequest.fastestInterval = 0
        mLocationRequest.numUpdates = 1

        mFusedLocationClient =
            LocationServices.getFusedLocationProviderClient(requireActivity())
        if (ActivityCompat.checkSelfPermission(
                mContext!!,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                mContext!!,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return
        }
        mFusedLocationClient.requestLocationUpdates(
            mLocationRequest, mLocationCallback,
            Looper.myLooper()
        )
    }

    private val mLocationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            val mLastLocation: Location = locationResult.lastLocation
            Applog.E("Latitude--22-->" + mLastLocation.latitude.toString() + " Longitude--" + mLastLocation.longitude.toString())
            moveToMyCurrentLocation(mLastLocation)
        }
    }

    private fun moveToMyCurrentLocation(mLastLocation: Location) {
        val current = LatLng(mLastLocation.latitude, mLastLocation.longitude)
        mMap.addMarker(
            MarkerOptions()
                .position(current)
                .anchor(0.5f, 0.5f)
                .title(getString(R.string.you_are_here))
        )
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(current, 15.0f))
    }
}
