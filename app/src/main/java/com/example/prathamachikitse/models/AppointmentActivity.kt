package com.example.prathamachikitse.appointment

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class AppointmentActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val name = EditText(this)
        val doctor = EditText(this)
        val date = EditText(this)
        val btn = Button(this)

        name.hint = "Patient Name"
        doctor.hint = "Doctor"
        date.hint = "Date"
        btn.text = "Book Appointment"

        btn.setOnClickListener {
            Toast.makeText(this, "Appointment Booked", Toast.LENGTH_SHORT).show()
        }

        val layout = LinearLayout(this)
        layout.orientation = LinearLayout.VERTICAL
        layout.setPadding(40, 40, 40, 40)

        layout.addView(name)
        layout.addView(doctor)
        layout.addView(date)
        layout.addView(btn)

        setContentView(layout)
    }
}