package com.example.prathamachikitse.data.repository

import com.example.prathamachikitse.data.MockData
import com.example.prathamachikitse.models.Patient
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class PatientRepository {
    // Switching to MockData for immediate local demonstration
    fun getAllPatients(): Flow<List<Patient>> = flow {
        emit(MockData.patients)
    }

    suspend fun getPatient(patientId: String): Patient? {
        return MockData.patients.find { it.id == patientId }
    }

    suspend fun savePatient(patient: Patient) {
        // No-op for mock data or could add to list if needed
    }
}
