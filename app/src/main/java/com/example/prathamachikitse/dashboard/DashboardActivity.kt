package com.example.prathamachikitse.dashboard

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class DashboardActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Simple UI without XML (no error)
        val textView = TextView(this)
        textView.text = "Welcome to Dashboard"
        textView.textSize = 20f

        setContentView(textView)
    }
}