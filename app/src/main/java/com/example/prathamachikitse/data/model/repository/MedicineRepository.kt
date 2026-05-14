package com.example.prathamachikitse.data.model.repository

import com.example.prathamachikitse.data.model.Medicine
import com.example.prathamachikitse.utils.FirebaseHelper.db
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow


class MedicineRepository {

    private val db = FirebaseFirestore.getInstance()

    fun getMedicines(): Flow<List<Medicine>> = callbackFlow {

        val listener = db.collection("medicines")
            .addSnapshotListener { value, error ->

                if (error != null) {
                    trySend(emptyList())
                    return@addSnapshotListener
                }

                val list = value?.documents?.map {

                    Medicine(
                        name = it.getString("name") ?: "",
                        time = it.getLong("time") ?: 0L,
                        verified = it.getBoolean("verified") ?: false
                    )

                } ?: emptyList()

                trySend(list)
            }

        awaitClose {
            listener.remove()
        }
    }
}

    fun addMedicine(name: String) {
        db.collection("medicines").add(
            mapOf(
                "name" to name,
                "time" to System.currentTimeMillis(),
                "verified" to true
            )
        )
    }


