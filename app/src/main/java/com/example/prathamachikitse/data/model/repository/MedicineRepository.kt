package com.example.prathamachikitse.data.model.repository

import com.example.prathamachikitse.data.MockData
import com.example.prathamachikitse.data.model.Medicine
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class MedicineRepository {

    fun getMedicines(): Flow<List<Medicine>> = flow {
        // Converting Mock Patient Medicines to the Medicine model used by the UI
        // Now including Patient Name for clarity
        val allMeds = MockData.patients.flatMap { p ->
            p.medicines.map { m ->
                Medicine(
                    id = "${p.id}_${m.name}",
                    name = m.name,
                    dosage = m.dosageMg,
                    timeToTake = m.timeToTake,
                    patientName = p.name,
                    patientId = p.id,
                    stock = m.quantityLeft,
                    isTaken = false
                )
            }
        }
        emit(allMeds)
    }

    fun addMedicine(medicine: Medicine) {
        // Local no-op for mock
    }

    fun toggleMedicineTaken(medicineId: String, isTaken: Boolean) {
        // Local no-op
    }
    
    fun updateStock(medicineId: String, newStock: Int) {
        // Local no-op
    }
}
