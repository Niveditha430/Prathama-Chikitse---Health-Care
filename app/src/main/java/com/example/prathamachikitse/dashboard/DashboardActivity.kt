package com.example.prathamachikitse.dashboard

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.appcompat.widget.Toolbar
import com.example.prathamachikitse.R
import com.example.prathamachikitse.ai.GeminiActivity
import com.example.prathamachikitse.models.AppointmentActivity
import com.example.prathamachikitse.firstaid.EmergencyGuidesActivity
import com.example.prathamachikitse.map.HospitalMapActivity
import com.example.prathamachikitse.telemedicine.TelemedicineActivity

class DashboardActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        // Fixed Dashboard Toolbar
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.title = "Dashboard"

        // Emergency Guides
        findViewById<CardView>(R.id.cardEmergency).setOnClickListener {
            startActivity(Intent(this, EmergencyGuidesActivity::class.java))
        }

        // Patient History / Records
        findViewById<CardView>(R.id.cardPatient).setOnClickListener {
            startActivity(Intent(this, PatientListActivity::class.java))
        }

        // Medicine Management (Shows all patient medicines)
        findViewById<CardView>(R.id.cardMedicine).setOnClickListener {
            startActivity(Intent(this, MedicineListActivity::class.java))
        }

        // Nearby Hospitals Map
        findViewById<CardView>(R.id.cardMap).setOnClickListener {
            startActivity(Intent(this, HospitalMapActivity::class.java))
        }

        // AI Health Assistant (Gemini)
        findViewById<CardView>(R.id.cardAI).setOnClickListener {
            startActivity(Intent(this, GeminiActivity::class.java))
        }

        // Telemedicine
        findViewById<CardView>(R.id.cardTelemedicine).setOnClickListener {
            startActivity(Intent(this, TelemedicineActivity::class.java))
        }
        
        // Appointments
        findViewById<CardView>(R.id.cardAppointment).setOnClickListener {
            startActivity(Intent(this, AppointmentActivity::class.java))
        }
    }
}
