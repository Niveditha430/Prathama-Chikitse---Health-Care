package com.example.prathamachikitse.telemedicine

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.prathamachikitse.R
import com.google.android.material.floatingactionbutton.FloatingActionButton

class VideoCallActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video_call)

        val doctorName = intent.getStringExtra("DOCTOR_NAME") ?: "Expert Doctor"
        findViewById<TextView>(R.id.tvCallingName).text = doctorName
        
        val tvStatus = findViewById<TextView>(R.id.tvCallStatus)
        val ivDoctor = findViewById<ImageView>(R.id.ivDoctorVideo)

        // Simulate connecting...
        Handler(Looper.getMainLooper()).postDelayed({
            tvStatus.text = "Connected"
            ivDoctor.visibility = View.VISIBLE
        }, 3000)

        findViewById<FloatingActionButton>(R.id.fabEndCall).setOnClickListener {
            finish()
        }
    }
}
