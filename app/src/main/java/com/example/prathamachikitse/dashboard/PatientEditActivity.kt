package com.example.prathamachikitse.dashboard

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.prathamachikitse.R
import com.example.prathamachikitse.ai.GeminiApi
import com.example.prathamachikitse.data.model.Medicine
import com.example.prathamachikitse.data.model.repository.MedicineRepository
import com.example.prathamachikitse.data.repository.PatientRepository
import com.example.prathamachikitse.models.MedicineInfo
import com.example.prathamachikitse.models.Patient
import com.google.android.material.textfield.TextInputEditText
import kotlinx.coroutines.launch
import java.util.UUID

class PatientEditActivity : AppCompatActivity() {

    private val patientRepo = PatientRepository()
    private val medicineRepo = MedicineRepository()
    private var patientId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_patient_edit)

        patientId = intent.getStringExtra("PATIENT_ID")

        val etName = findViewById<TextInputEditText>(R.id.etName)
        val etBloodGroup = findViewById<TextInputEditText>(R.id.etBloodGroup)
        val etDisease = findViewById<TextInputEditText>(R.id.etDisease)
        val etDoctor = findViewById<TextInputEditText>(R.id.etDoctor)
        val etEmergency = findViewById<TextInputEditText>(R.id.etEmergency)
        val etCaretaker = findViewById<TextInputEditText>(R.id.etCaretaker)
        val etMedicines = findViewById<EditText>(R.id.etMedicines)
        val btnSave = findViewById<Button>(R.id.btnSave)
        val btnAiFill = findViewById<Button>(R.id.btnAiFill)

        patientId?.let { id ->
            lifecycleScope.launch {
                patientRepo.getPatient(id)?.let { p ->
                    etName.setText(p.name)
                    etBloodGroup.setText(p.bloodGroup)
                    etDisease.setText(p.disease)
                    etDoctor.setText(p.assignedDoctor)
                    etEmergency.setText(p.emergencyNumber)
                    etCaretaker.setText(p.careTakerName)
                    etMedicines.setText(p.medicines.joinToString("\n") { "${it.name}, ${it.dosageMg}, ${it.timeToTake}, ${it.quantityLeft}" })
                }
            }
        }

        btnAiFill.setOnClickListener {
            val disease = etDisease.text.toString()
            if (disease.isEmpty()) {
                Toast.makeText(this, "Enter a condition first!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            etName.setText("Patient Record")
            etDoctor.setText("Specialist for $disease")
            etMedicines.setText("Medicine Name, 500mg, 8AM, 10")
            Toast.makeText(this, "AI draft applied. Please edit details.", Toast.LENGTH_SHORT).show()
        }

        btnSave.setOnClickListener {
            val pName = etName.text.toString().trim()
            val finalId = patientId ?: UUID.randomUUID().toString()
            
            val medList = mutableListOf<MedicineInfo>()
            val lines = etMedicines.text.toString().split("\n")
            for (line in lines) {
                val p = line.split(",").map { it.trim() }
                if (p.isNotEmpty() && p[0].isNotBlank()) {
                    val m = MedicineInfo(p[0], p.getOrElse(1){"500mg"}, p.getOrElse(2){"8AM"}, p.getOrElse(3){"10"}.toIntOrNull() ?: 10)
                    medList.add(m)
                    
                    // Sync to 'My Medicines' tab with Patient linking
                    medicineRepo.addMedicine(Medicine(
                        name = m.name, 
                        dosage = m.dosageMg, 
                        timeToTake = m.timeToTake,
                        patientName = pName,
                        patientId = finalId,
                        stock = m.quantityLeft, 
                        verified = true
                    ))
                }
            }

            val updatedPatient = Patient(
                id = finalId,
                name = pName,
                bloodGroup = etBloodGroup.text.toString(),
                disease = etDisease.text.toString(),
                assignedDoctor = etDoctor.text.toString(),
                emergencyNumber = etEmergency.text.toString(),
                careTakerName = etCaretaker.text.toString(),
                medicines = medList
            )

            lifecycleScope.launch {
                patientRepo.savePatient(updatedPatient)
                Toast.makeText(this@PatientEditActivity, "History Saved & Medicines Synced!", Toast.LENGTH_LONG).show()
                finish()
            }
        }
    }
}
