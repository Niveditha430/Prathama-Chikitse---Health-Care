package com.example.prathamachikitse.utils

import com.google.firebase.firestore.FirebaseFirestore

object FirebaseHelper {
    val db = FirebaseFirestore.getInstance()

    fun savePatient(name: String, age: String, blood: String) {
        val patient = hashMapOf(
            "name" to name,
            "age" to age,
            "blood" to blood
        )

        db.collection("patients")
            .add(patient)
    }
}