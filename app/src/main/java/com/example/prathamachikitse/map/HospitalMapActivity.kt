package com.example.prathamachikitse.map

import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import com.example.prathamachikitse.R
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class HospitalMapActivity : FragmentActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_hospital_map)

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment

        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {

        mMap = googleMap

        val hospital = LatLng(12.9716, 77.5946)

        mMap.addMarker(
            MarkerOptions().position(hospital).title("Hospital")
        )

        mMap.moveCamera(
            CameraUpdateFactory.newLatLngZoom(hospital, 15f)
        )
    }
}