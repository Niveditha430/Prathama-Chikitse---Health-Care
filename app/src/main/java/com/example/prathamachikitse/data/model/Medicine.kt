package com.example.prathamachikitse.data.model

data class Medicine(
    val id: String = "",
    val name: String = "",
    val dosage: String = "",
    val timeToTake: String = "",
    val patientName: String = "",
    val patientId: String = "",
    val isTaken: Boolean = false,
    val stock: Int = 0,
    val threshold: Int = 5,
    val lastTakenTime: Long = 0,
    val verified: Boolean = false
) {
    val needsReorder: Boolean get() = stock <= threshold
}