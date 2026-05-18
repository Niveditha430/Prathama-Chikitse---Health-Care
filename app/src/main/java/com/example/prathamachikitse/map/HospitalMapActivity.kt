package com.example.prathamachikitse.map

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.app.ActivityCompat
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.prathamachikitse.R
import com.example.prathamachikitse.data.MockData
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class HospitalMapActivity : FragmentActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var rvHospitals: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hospital_map)

        findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolbar).setNavigationOnClickListener { finish() }

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        
        rvHospitals = findViewById(R.id.rvHospitals)
        rvHospitals.layoutManager = LinearLayoutManager(this)
        rvHospitals.adapter = HospitalAdapter(MockData.hospitals) { hospital ->
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(hospital.latLng, 16f))
        }

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), 101)
        } else {
            enableMyLocation()
        }
        
        addHospitalMarkers()
    }

    private fun enableMyLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            mMap.isMyLocationEnabled = true
            fusedLocationClient.lastLocation.addOnSuccessListener { location ->
                val currentPos = if (location != null) LatLng(location.latitude, location.longitude) else LatLng(12.9716, 77.5946)
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentPos, 14f))
            }
        }
    }

    private fun addHospitalMarkers() {
        for (h in MockData.hospitals) {
            val markerColor = if (h.type == "Government") BitmapDescriptorFactory.HUE_AZURE else BitmapDescriptorFactory.HUE_RED
            mMap.addMarker(MarkerOptions()
                .position(h.latLng)
                .title(h.name)
                .snippet("Dist: ${h.distance} | ${h.timing}")
                .icon(BitmapDescriptorFactory.defaultMarker(markerColor)))
        }
    }

    class HospitalAdapter(private val list: List<MockData.Hospital>, private val onClick: (MockData.Hospital) -> Unit) : 
        RecyclerView.Adapter<HospitalAdapter.VH>() {
        
        class VH(v: View) : RecyclerView.ViewHolder(v) {
            val name: TextView = v.findViewById(R.id.tvHospitalName)
            val doctor: TextView = v.findViewById(R.id.tvDoctorName)
            val details: TextView = v.findViewById(R.id.tvHospitalDetails)
            val type: TextView = v.findViewById(R.id.tvTypeTag)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
            val v = LayoutInflater.from(parent.context).inflate(R.layout.item_hospital, parent, false)
            return VH(v)
        }

        override fun onBindViewHolder(holder: VH, position: Int) {
            val h = list[position]
            holder.name.text = h.name
            holder.doctor.text = h.doctor
            holder.details.text = "${h.distance} | ${h.timing}"
            holder.type.text = h.type
            holder.itemView.setOnClickListener { onClick(h) }
        }

        override fun getItemCount() = list.size
    }
}
