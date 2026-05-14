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

    fun addMedicine(name: String) {
        repo.run { addMedicine(name) }
    }
}