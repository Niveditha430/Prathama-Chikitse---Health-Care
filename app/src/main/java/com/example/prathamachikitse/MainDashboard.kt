package com.example.prathamachikitse

import android.content.Intent
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MedicalServices
import androidx.compose.material.icons.filled.LocalHospital
import com.example.prathamachikitse.dashboard.MedicineListActivity
import com.example.prathamachikitse.dashboard.PatientActivity

@Composable
fun MainDashboard() {

    val context = LocalContext.current
    var selected by remember { mutableStateOf(1) }

    Scaffold(
        bottomBar = {
            NavigationBar {

                // 🔹 Medicines
                NavigationBarItem(
                    selected = selected == 1,
                    onClick = {
                        selected = 1
                        context.startActivity(
                            Intent(context, MedicineListActivity::class.java)
                        )
                    },
                    label = { Text("Medicines") },
                    icon = {
                        Icon(
                            Icons.Filled.MedicalServices,
                            contentDescription = "Medicines"
                        )
                    }
                )

                // 🔹 Hospitals
                NavigationBarItem(
                    selected = selected == 2,
                    onClick = {
                        selected = 2
                        context.run {
                            startActivity(
                                                Intent(this, PatientActivity::class.java)
                                            )
                        }
                    },
                    label = { Text("Hospitals") },
                    icon = {
                        Icon(
                            Icons.Filled.LocalHospital,
                            contentDescription = "Hospitals"
                        )
                    }
                )

                // 🔹 Third tab (optional)
                NavigationBarItem(
                    selected = selected == 3,
                    onClick = {
                        selected = 3
                    },
                    label = { Text("More") },
                    icon = {
                        Icon(
                            Icons.Filled.MedicalServices,
                            contentDescription = "More"
                        )
                    }
                )
            }
        }
    ) {
    }
}
