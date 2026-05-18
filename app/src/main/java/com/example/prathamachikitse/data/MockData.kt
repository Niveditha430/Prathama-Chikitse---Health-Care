package com.example.prathamachikitse.data

import com.example.prathamachikitse.models.Patient
import com.example.prathamachikitse.models.MedicineInfo
import com.google.android.gms.maps.model.LatLng

object MockData {
    val patients = mutableListOf(
        Patient(
            id = "p1",
            name = "Rajesh Kumar",
            age = 45,
            gender = "Male",
            weight = "75kg",
            bloodGroup = "O+",
            phoneNumber = "9845012345",
            disease = "Hypertension & Type 2 Diabetes",
            assignedDoctor = "Dr. Ramesh (Gen Surgeon)",
            nextVisitDate = "2024-12-15",
            reportSummary = "Patient has chronic hypertension. Blood sugar levels are fluctuating. Requires strict diet and morning walks.",
            emergencyNumber = "9876543210",
            careTakerName = "Sunita Kumar",
            careTakerNumber = "9876543211",
            medicines = listOf(
                MedicineInfo("Amlodipine", "5mg", "09:00 AM (After Food)", 15),
                MedicineInfo("Metformin", "500mg", "08:00 PM (Before Food)", 10),
                MedicineInfo("Telmisartan", "40mg", "10:00 AM", 20)
            ),
            bpHistory = listOf(145.0, 140.0, 138.0, 142.0, 135.0, 132.0, 128.0),
            sugarHistory = listOf(190.0, 185.0, 180.0, 175.0, 170.0, 160.0, 155.0)
        ),
        Patient(
            id = "p2",
            name = "Anjali Sharma",
            age = 32,
            gender = "Female",
            weight = "60kg",
            bloodGroup = "A-",
            phoneNumber = "8877665544",
            disease = "Chronic Asthma",
            assignedDoctor = "Dr. Sunita (Cardiologist)",
            nextVisitDate = "2024-11-20",
            reportSummary = "Conditions stable. Inhaler usage reduced. Avoid allergens and cold weather.",
            emergencyNumber = "9123456789",
            careTakerName = "Rohan Sharma",
            careTakerNumber = "9123456780",
            medicines = listOf(
                MedicineInfo("Salbutamol Inhaler", "100mcg", "When Short of Breath", 1),
                MedicineInfo("Montelukast", "10mg", "09:00 PM", 20)
            ),
            bpHistory = listOf(110.0, 112.0, 115.0, 112.0, 110.0, 114.0, 112.0),
            sugarHistory = listOf(90.0, 95.0, 92.0, 98.0, 94.0, 90.0, 92.0)
        ),
        Patient(
            id = "p3",
            name = "Amit Patel",
            age = 68,
            gender = "Male",
            weight = "82kg",
            bloodGroup = "B+",
            phoneNumber = "7766554433",
            disease = "Post-Cardiac Recovery",
            assignedDoctor = "Dr. Kapoor (Orthopedic)",
            nextVisitDate = "2024-12-05",
            reportSummary = "Improving post-surgery. Vitals show positive trend. Low salt diet advised.",
            emergencyNumber = "8887776665",
            careTakerName = "Priya Patel",
            careTakerNumber = "8887776664",
            medicines = listOf(
                MedicineInfo("Atorvastatin", "20mg", "10:00 PM", 30),
                MedicineInfo("Clopidogrel", "75mg", "10:00 AM", 25),
                MedicineInfo("Aspirin", "75mg", "10:00 AM", 30)
            ),
            bpHistory = listOf(130.0, 128.0, 125.0, 122.0, 120.0, 118.0, 115.0),
            sugarHistory = listOf(110.0, 105.0, 108.0, 112.0, 110.0, 105.0, 102.0)
        ),
        Patient(
            id = "p4",
            name = "Suresh Raina",
            age = 55,
            gender = "Male",
            weight = "78kg",
            bloodGroup = "B+",
            phoneNumber = "9900887766",
            disease = "Arthritis & BP Control",
            assignedDoctor = "Dr. Anil (Pediatrician)",
            nextVisitDate = "2024-12-01",
            reportSummary = "Joint pain managed. BP is stable with medication.",
            emergencyNumber = "9988776655",
            careTakerName = "Deepa Raina",
            careTakerNumber = "9988776654",
            medicines = listOf(
                MedicineInfo("Naproxen", "500mg", "08:00 AM", 12),
                MedicineInfo("Calcium D3", "60k", "Once Weekly", 4)
            ),
            bpHistory = listOf(125.0, 127.0, 124.0, 126.0, 125.0),
            sugarHistory = listOf(105.0, 108.0, 102.0, 100.0, 104.0)
        ),
        Patient(
            id = "p5",
            name = "Sneha Hegde",
            age = 29,
            gender = "Female",
            weight = "54kg",
            bloodGroup = "AB+",
            phoneNumber = "9000111222",
            disease = "Migraine & Low BP",
            assignedDoctor = "Dr. Meena (Neurologist)",
            nextVisitDate = "2024-12-10",
            reportSummary = "Acute attacks monitored. Hydration is key.",
            emergencyNumber = "9001122334",
            careTakerName = "Mahesh Hegde",
            careTakerNumber = "9001122335",
            medicines = listOf(
                MedicineInfo("Sumatriptan", "50mg", "During Attack", 6),
                MedicineInfo("Propranolol", "20mg", "09:00 AM", 30)
            ),
            bpHistory = listOf(105.0, 108.0, 102.0, 100.0, 104.0),
            sugarHistory = listOf(85.0, 88.0, 90.0, 87.0, 89.0)
        )
    )

    val appointments = mutableListOf(
        mapOf("patient" to "Rajesh Kumar", "doctor" to "Dr. Ramesh (Gen Surgeon)", "date" to "15/12/2024"),
        mapOf("patient" to "Anjali Sharma", "doctor" to "Dr. Sunita (Cardiologist)", "date" to "20/11/2024"),
        mapOf("patient" to "Amit Patel", "doctor" to "Dr. Kapoor (Orthopedic)", "date" to "05/12/2024")
    )

    data class Hospital(
        val name: String,
        val doctor: String,
        val distance: String,
        val timing: String,
        val type: String,
        val latLng: LatLng
    )

    val hospitals = listOf(
        Hospital("Victoria Public Hospital", "Dr. Ramesh (Gen Surgeon)", "1.2 km", "Open 24/7", "Government", LatLng(12.9650, 77.5750)),
        Hospital("Apollo Medical Center", "Dr. Sunita (Cardiologist)", "2.5 km", "09:00 AM - 09:00 PM", "Private", LatLng(12.9780, 77.5900)),
        Hospital("Fortis Health Care", "Dr. Kapoor (Orthopedic)", "3.8 km", "Open 24/7", "Private", LatLng(12.9900, 77.5850)),
        Hospital("Sagar Hospital", "Dr. Anil (Pediatrician)", "4.1 km", "08:00 AM - 10:00 PM", "Private", LatLng(12.9300, 77.5950)),
        Hospital("Government Medical College", "Dr. Meena (Neurologist)", "1.5 km", "Open 24/7", "Government", LatLng(12.9700, 77.5600)),
        Hospital("Narayana Health", "Dr. Shiv (Cardiac Specialists)", "5.2 km", "Open 24/7", "Private", LatLng(12.9400, 77.6100))
    )
}
