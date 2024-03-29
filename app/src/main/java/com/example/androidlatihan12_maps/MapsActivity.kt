package com.example.androidlatihan12_maps

import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.location.Location
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.leinardi.android.speeddial.SpeedDialActionItem
import kotlinx.android.synthetic.main.activity_maps.*

class MapsActivity : AppCompatActivity(), OnMapReadyCallback,
GoogleMap.OnMarkerClickListener{
    override fun onMarkerClick(p0: Marker?) = false

    private lateinit var mMap: GoogleMap
    private lateinit var fusedLocationProviderClient : FusedLocationProviderClient
    private lateinit var lastLocation : Location

    companion object {
        private const val LOCATION_PERMISSION = 1
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)


        speedDial.addActionItem(
            SpeedDialActionItem.Builder(R.id.normal, R.drawable.ic_normal_24dp)
                .setLabel("Normal")
                .create()
        )
        speedDial.addActionItem(
            SpeedDialActionItem.Builder(R.id.satellite, R.drawable.ic_satellite_24dp)
                .setLabel("Satellite")
                .create()
        )
        speedDial.addActionItem(
            SpeedDialActionItem.Builder(R.id.hybrid, R.drawable.ic_hybrid__24dp)
                .setLabel("Hybrid")
                .create()
        )
        speedDial.addActionItem(
            SpeedDialActionItem.Builder(R.id.terrain, R.drawable.ic_terrain_24dp)
                .setLabel("Terrain")
                .create()
        )
        speedDial.setOnActionSelectedListener {
            when (it.id) {
                R.id.normal -> {
                    mMap.mapType = GoogleMap.MAP_TYPE_NORMAL
                }
                R.id.satellite -> {
                    mMap.mapType = GoogleMap.MAP_TYPE_SATELLITE
                }
                R.id.terrain -> {
                    mMap.mapType = GoogleMap.MAP_TYPE_TERRAIN
                }
                R.id.hybrid -> {
                    mMap.mapType = GoogleMap.MAP_TYPE_HYBRID
                }
            }
            false
        }
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        mMap.uiSettings.isZoomControlsEnabled = true
        mMap.setOnMarkerClickListener(this)
        settingUpMap()

        // Add a marker in Sydney and move the camera

    }

    private fun settingUpMap() {
        if (ActivityCompat.checkSelfPermission(
                this,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) !=
            PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
                LOCATION_PERMISSION
            )
            return
        }
        mMap.isMyLocationEnabled = true
        fusedLocationProviderClient.lastLocation.addOnSuccessListener(this) { location ->
            if (location != null) {
                lastLocation = location
                val currentPost = LatLng(location.latitude, location.longitude)
                placeMarkerInMaps(currentPost)
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(currentPost, 18.0f))
            }
        }
    }

    private fun placeMarkerInMaps(loc: LatLng) {
        val markerOptions = MarkerOptions().position(loc)
        mMap.addMarker(markerOptions)
    }
}

//        val sarkom = LatLng(-6.164635, 106.7627513)
//        mMap.addMarker(MarkerOptions().position(sarkom).title("Sarang Komodo"))
//        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sarkom, 17.0f))
//        mMap.mapType = GoogleMap.MAP_TYPE_NORMAL
//        val markerOptions = MarkerOptions().position(sarkom)
//        markerOptions.icon(BitmapDescriptorFactory.fromBitmap(
//            BitmapFactory.decodeResource(resources, R.mipmap.ic_spongbob)
//        ))
//        mMap.addMarker(markerOptions.title("Sarang Komodo"))

//        mMap.uiSettings.isZoomControlsEnabled = true
//        mMap.setOnMarkerClickListener ( this )
//        settingUpMaps()
//    }
//    private fun settingUpMaps(){
//        if(ActivityCompat.checkSelfPermission(this,
//                android.Manifest.permission.ACCESS_FINE_LOCATION) !=
//                PackageManager.PERMISSION_GRANTED){
//            ActivityCompat.requestPermissions(this,
//                arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
//                LOCATION_PERMISSION)
//            return
//        }
//        mMap.isMyLocationEnabled = true
//        fusedLocationProviderClient.lastLocation.addOnSuccessListener (this){
//            location ->
//            if (location != null){
//                lastLocation = location
//                val currentPost = LatLng(location.latitude, location.longitude)
//                placeMarkerInMaps(currentPost)
//                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(currentPost
//                , 18.0f))
//            }
//        }
//    }
//
//    fun placeMarkerInMaps(loc: LatLng) {
//        val markerOptions = MarkerOptions().position(loc)
//        markerOptions.icon(BitmapDescriptorFactory.fromBitmap(
//            BitmapFactory.decodeResource(resources, R.mipmap.ic_spongbob)
//        ))
//        mMap.addMarker(markerOptions)
//    }
//}
