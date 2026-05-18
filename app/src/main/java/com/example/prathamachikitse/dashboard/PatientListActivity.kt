package com.example.prathamachikitse.dashboard

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.prathamachikitse.R
import com.example.prathamachikitse.data.repository.PatientRepository
import com.example.prathamachikitse.models.Patient
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.launch

class PatientListActivity : AppCompatActivity() {

    private val patientRepo = PatientRepository()
    private lateinit var rvPatients: RecyclerView
    private lateinit var adapter: PatientAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_patient_list)

        findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolbar).setNavigationOnClickListener { finish() }

        rvPatients = findViewById(R.id.rvPatients)
        rvPatients.layoutManager = LinearLayoutManager(this)
        
        findViewById<FloatingActionButton>(R.id.fabAddPatient).setOnClickListener {
            startActivity(Intent(this, PatientEditActivity::class.java))
        }
    }

    override fun onResume() {
        super.onResume()
        loadPatients()
    }

    private fun loadPatients() {
        lifecycleScope.launch {
            patientRepo.getAllPatients().collect { patients ->
                adapter = PatientAdapter(patients) { patient ->
                    val intent = Intent(this@PatientListActivity, PatientDetailActivity::class.java)
                    intent.putExtra("PATIENT_ID", patient.id)
                    startActivity(intent)
                }
                rvPatients.adapter = adapter
            }
        }
    }

    class PatientAdapter(private val list: List<Patient>, private val onClick: (Patient) -> Unit) : 
        RecyclerView.Adapter<PatientAdapter.VH>() {
        
        class VH(v: View) : RecyclerView.ViewHolder(v) {
            val name: TextView = v.findViewById(R.id.tvPatientName)
            val ageBlood: TextView = v.findViewById(R.id.tvPatientAgeBlood)
            val condition: TextView = v.findViewById(R.id.tvPatientCondition)
            val meds: TextView = v.findViewById(R.id.tvPatientMeds)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
            val v = LayoutInflater.from(parent.context).inflate(R.layout.item_patient, parent, false)
            return VH(v)
        }

        override fun onBindViewHolder(holder: VH, position: Int) {
            val p = list[position]
            holder.name.text = p.name
            holder.ageBlood.text = "Age: ${p.age} | Blood: ${p.bloodGroup}"
            holder.condition.text = p.disease
            val medsList = p.medicines.joinToString { it.name }
            holder.meds.text = "Meds: $medsList"

            holder.itemView.setOnClickListener { onClick(p) }
        }

        override fun getItemCount() = list.size
    }
}
