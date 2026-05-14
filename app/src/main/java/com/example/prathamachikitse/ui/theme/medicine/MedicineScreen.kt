package com.example.prathamachikitse.ui.medicine

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.prathamachikitse.viewmodel.MedicineViewModel

@Composable
fun MedicineScreen(viewModel: MedicineViewModel = viewModel()) {

    val medicines by viewModel.medicines.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        Text(
            text = "💊 Medicines",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(10.dp))

        LazyColumn {

            items(medicines) { med ->

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    elevation = CardDefaults.cardElevation(6.dp)
                ) {

                    Column(modifier = Modifier.padding(16.dp)) {

                        Text("Name: ${med.name}")

                        Spacer(modifier = Modifier.height(5.dp))

                        if (med.verified) {
                            Text(
                                text = "✔ Verified",
                                color = MaterialTheme.colorScheme.primary
                            )
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        Button(
            onClick = {
                viewModel.addMedicine("Paracetamol")
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Add Medicine")
        }
    }
}