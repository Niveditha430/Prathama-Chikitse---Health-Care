package com.example.prathamachikitse.telemedicine

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.prathamachikitse.R
import com.example.prathamachikitse.ui.theme.AppTheme

class TelemedicineActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppTheme {
                TelemedicineScreen(onBack = { finish() })
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TelemedicineScreen(onBack: () -> Unit) {
    val context = LocalContext.current

    val doctors = listOf(
        Doctor("Dr. ARUN KUMAR", "General Surgeon", "4.9", R.drawable.ic_patient),
        Doctor("Dr. Ananya Rao", "Cardiologist", "4.8", R.drawable.ic_patient),
        Doctor("Dr. Vikram Singh", "First Aid Specialist", "4.9", R.drawable.ic_patient),
        Doctor("Dr. Priya Sharma", "Pediatrician", "4.7", R.drawable.ic_patient)
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Telehealth Experts", color = Color(0xFF1E293B), fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = null, tint = Color(0xFF1E293B))
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White)
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(Color(0xFFF8FAFC)) // Light Professional Background
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(0.dp)
            ) {
                Row(
                    modifier = Modifier.padding(20.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text("Online Consultation", color = Color(0xFF0F172A), fontSize = 20.sp, fontWeight = FontWeight.Bold)
                        Text("Connect with experts instantly", color = Color(0xFF64748B), fontSize = 14.sp)
                    }
                    Icon(Icons.Default.VideoCall, contentDescription = null, tint = Color(0xFF22C55E), modifier = Modifier.size(40.dp))
                }
            }

            Text(
                "Available Experts",
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF475569)
            )

            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(doctors) { doc ->
                    DoctorCard(doc) { 
                        val intent = Intent(context, VideoCallActivity::class.java)
                        intent.putExtra("DOCTOR_NAME", doc.name)
                        context.startActivity(intent)
                    }
                }
            }
        }
    }
}

@Composable
fun DoctorCard(doc: Doctor, onCallClick: (Doctor) -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(0.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(50.dp)
                    .clip(CircleShape)
                    .background(Color(0xFFF1F5F9)),
                contentAlignment = Alignment.Center
            ) {
                Icon(Icons.Default.Person, contentDescription = null, tint = Color(0xFF64748B), modifier = Modifier.size(28.dp))
            }
            
            Spacer(modifier = Modifier.width(16.dp))
            
            Column(modifier = Modifier.weight(1f)) {
                Text(doc.name, fontWeight = FontWeight.Bold, fontSize = 16.sp, color = Color(0xFF1E293B))
                Text(doc.specialty, color = Color(0xFF64748B), fontSize = 13.sp)
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.Star, contentDescription = null, tint = Color(0xFFF59E0B), modifier = Modifier.size(14.dp))
                    Text(" ${doc.rating}", fontWeight = FontWeight.SemiBold, fontSize = 13.sp, color = Color(0xFF475569))
                }
            }
            
            Button(
                onClick = { onCallClick(doc) },
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFF1F5F9), contentColor = Color(0xFF2563EB)),
                elevation = ButtonDefaults.buttonElevation(0.dp)
            ) {
                Icon(Icons.Default.VideoCall, contentDescription = null, modifier = Modifier.size(18.dp))
                Spacer(modifier = Modifier.width(4.dp))
                Text("CALL", fontSize = 12.sp, fontWeight = FontWeight.Bold)
            }
        }
    }
}

data class Doctor(val name: String, val specialty: String, val rating: String, val image: Int)
