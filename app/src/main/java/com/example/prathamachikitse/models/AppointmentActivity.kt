package com.example.prathamachikitse.models

import android.app.DatePickerDialog
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.prathamachikitse.R
import com.example.prathamachikitse.data.MockData
import java.util.*

class AppointmentActivity : AppCompatActivity() {

    private var selectedDate: String = ""
    private lateinit var historyContainer: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_appointment)

        findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolbar).setNavigationOnClickListener { finish() }

        val spinnerPatient = findViewById<Spinner>(R.id.spinnerPatient)
        val spinnerDoctor = findViewById<Spinner>(R.id.spinnerDoctor)
        val btnDatePicker = findViewById<Button>(R.id.btnDatePicker)
        val btnBook = findViewById<Button>(R.id.btnBook)
        historyContainer = findViewById(R.id.historyContainer)

        // Data from MockData
        val patientNames = MockData.patients.map { it.name }.toMutableList()
        patientNames.add(0, "Self")
        
        val doctors = arrayOf(
            "Dr. Smith (General)",
            "Dr. Johnson (Cardiologist)",
            "Dr. Williams (Pediatrician)",
            "Dr. Brown (Orthopedic)",
            "Dr. Arun Kumar (Emergency)"
        )

        spinnerPatient.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, patientNames)
        spinnerDoctor.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, doctors)

        btnDatePicker.setOnClickListener {
            val c = Calendar.getInstance()
            DatePickerDialog(this, { _, y, m, d ->
                selectedDate = "$d/${m + 1}/$y"
                btnDatePicker.text = "Date: $selectedDate"
            }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH)).show()
        }

        btnBook.setOnClickListener {
            if (selectedDate.isEmpty()) {
                Toast.makeText(this, "Please select a date", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            
            val patient = spinnerPatient.selectedItem.toString()
            val doctor = spinnerDoctor.selectedItem.toString()
            
            // Add to history
            MockData.appointments.add(0, mapOf("patient" to patient, "doctor" to doctor, "date" to selectedDate))
            updateHistoryUI()
            
            Toast.makeText(this, "Appointment confirmed!", Toast.LENGTH_LONG).show()
            selectedDate = ""
            btnDatePicker.text = "Select Date"
        }

        updateHistoryUI()
    }

    private fun updateHistoryUI() {
        historyContainer.removeAllViews()
        for (app in MockData.appointments) {
            val card = layoutInflater.inflate(android.R.layout.simple_list_item_2, historyContainer, false)
            val text1 = card.findViewById<TextView>(android.R.id.text1)
            val text2 = card.findViewById<TextView>(android.R.id.text2)
            
            text1.text = "${app["patient"]} - ${app["doctor"]}"
            text1.setTextColor(Color.parseColor("#1e293b"))
            text1.textSize = 16f
            
            text2.text = "Date: ${app["date"]}"
            text2.setTextColor(Color.parseColor("#64748b"))
            
            card.setPadding(32, 24, 32, 24)
            historyContainer.addView(card)
            
            val divider = View(this).apply {
                layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 1)
                setBackgroundColor(Color.parseColor("#e2e8f0"))
            }
            historyContainer.addView(divider)
        }
    }
}
