package com.example.prathamachikitse.ui.medicine

import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.prathamachikitse.data.model.Medicine
import com.example.prathamachikitse.viewmodel.MedicineViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MedicineScreen(viewModel: MedicineViewModel = viewModel()) {
    val medicines by viewModel.medicines.collectAsState()
    var showAddDialog by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Prescribed Medicines", fontWeight = FontWeight.Bold) },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = Color(0xFFE3F2FD)
                )
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { showAddDialog = true },
                containerColor = Color(0xFF1976D2),
                contentColor = Color.White
            ) {
                Icon(Icons.Default.Add, contentDescription = "Add Medicine")
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(Color(0xFFE3F2FD), Color.White)
                    )
                )
        ) {
            LazyColumn(
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(medicines) { med ->
                    MedicineItem(
                        medicine = med,
                        onToggleTaken = { viewModel.toggleTaken(med.id, it) }
                    )
                }
            }
        }
    }

    if (showAddDialog) {
        AddMedicineDialog(
            onDismiss = { showAddDialog = false },
            onConfirm = { name, dosage, stock ->
                viewModel.addMedicine(name, dosage, stock)
                showAddDialog = false
            }
        )
    }
}

@Composable
fun MedicineItem(
    medicine: Medicine,
    onToggleTaken: (Boolean) -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(4.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (medicine.isTaken) Color(0xFFF1F8E9) else Color.White
        )
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = medicine.name,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF0D47A1)
                )
                
                // Show Patient Name
                if (medicine.patientName.isNotEmpty()) {
                    Text(
                        text = "Patient: ${medicine.patientName}",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color(0xFFD32F2F),
                        fontWeight = FontWeight.SemiBold
                    )
                }
                
                Text(
                    text = "Dosage: ${medicine.dosage} | Time: ${medicine.timeToTake}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Gray
                )
                Text(
                    text = "In Stock: ${medicine.stock}",
                    style = MaterialTheme.typography.bodySmall,
                    color = if (medicine.needsReorder) Color.Red else Color.DarkGray
                )
            }

            IconButton(onClick = { onToggleTaken(!medicine.isTaken) }) {
                Icon(
                    imageVector = if (medicine.isTaken) Icons.Default.CheckCircle else Icons.Default.RadioButtonUnchecked,
                    contentDescription = "Mark as taken",
                    tint = if (medicine.isTaken) Color(0xFF4CAF50) else Color.Gray,
                    modifier = Modifier.size(32.dp)
                )
            }
        }
    }
}

@Composable
fun AddMedicineDialog(
    onDismiss: () -> Unit,
    onConfirm: (String, String, Int) -> Unit
) {
    var name by remember { mutableStateOf("") }
    var dosage by remember { mutableStateOf("") }
    var stock by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Add New Medicine") },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                OutlinedTextField(value = name, onValueChange = { name = it }, label = { Text("Medicine Name") })
                OutlinedTextField(value = dosage, onValueChange = { dosage = it }, label = { Text("Dosage") })
                OutlinedTextField(value = stock, onValueChange = { stock = it }, label = { Text("Stock Count") })
            }
        },
        confirmButton = {
            Button(onClick = { onConfirm(name, dosage, stock.toIntOrNull() ?: 0) }) {
                Text("Add")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) { Text("Cancel") }
        }
    )
}
