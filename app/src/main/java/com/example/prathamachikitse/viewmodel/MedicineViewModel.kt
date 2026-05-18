package com.example.prathamachikitse.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.prathamachikitse.data.model.Medicine
import com.example.prathamachikitse.data.model.repository.MedicineRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class MedicineViewModel : ViewModel() {

    private val repo = MedicineRepository()

    private val _medicines = MutableStateFlow<List<Medicine>>(emptyList())
    val medicines: StateFlow<List<Medicine>> = _medicines

    init {
        viewModelScope.launch {
            repo.getMedicines().collect {
                _medicines.value = it
            }
        }
    }

    fun addMedicine(name: String, dosage: String = "", stock: Int = 10) {
        viewModelScope.launch {
            repo.addMedicine(Medicine(name = name, dosage = dosage, stock = stock))
        }
    }

    fun toggleTaken(medicineId: String, isTaken: Boolean) {
        viewModelScope.launch {
            repo.toggleMedicineTaken(medicineId, isTaken)
        }
    }

    fun updateStock(medicineId: String, newStock: Int) {
        viewModelScope.launch {
            repo.updateStock(medicineId, newStock)
        }
    }
}
