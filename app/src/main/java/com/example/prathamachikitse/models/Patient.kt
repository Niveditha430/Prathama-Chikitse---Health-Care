package com.example.prathamachikitse.models

data class Patient(
    val id: String = "",
    val name: String = "",
    val age: Int = 0,
    val gender: String = "",
    val weight: String = "",
    val bloodGroup: String = "",
    val phoneNumber: String = "",
    val disease: String = "",
    val assignedDoctor: String = "",
    val nextVisitDate: String = "",
    val reportSummary: String = "",
    val emergencyNumber: String = "",
    val careTakerName: String = "",
    val careTakerNumber: String = "",
    val medicines: List<MedicineInfo> = emptyList(),
    val bpHistory: List<Double> = emptyList(),
    val sugarHistory: List<Double> = emptyList()
)

data class MedicineInfo(
    val name: String = "",
    val dosageMg: String = "",
    val timeToTake: String = "",
    val quantityLeft: Int = 0
)
