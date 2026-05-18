package com.example.prathamachikitse.dashboard

import android.content.Intent
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Typeface
import android.graphics.pdf.PdfDocument
import android.os.Bundle
import android.os.Environment
import android.speech.tts.TextToSpeech
import android.text.Html
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.prathamachikitse.R
import com.example.prathamachikitse.ai.GeminiApi
import com.example.prathamachikitse.data.repository.PatientRepository
import com.example.prathamachikitse.models.Patient
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream
import java.util.*

class PatientDetailActivity : AppCompatActivity(), TextToSpeech.OnInitListener {

    private val patientRepo = PatientRepository()
    private var currentPatient: Patient? = null
    private lateinit var tts: TextToSpeech
    private var lastAiResponse: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_patient_detail)

        tts = TextToSpeech(this, this)

        findViewById<ImageButton>(R.id.btnEditPatient).setOnClickListener {
            val intent = Intent(this, PatientEditActivity::class.java)
            intent.putExtra("PATIENT_ID", currentPatient?.id)
            startActivity(intent)
        }

        findViewById<Button>(R.id.btnGenerateReport).setOnClickListener {
            generateAIReport()
        }

        findViewById<TextView>(R.id.tvSummary).setOnClickListener {
            if (lastAiResponse.isNotEmpty()) {
                showVoiceOptions()
            }
        }
        
        findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolbar).setNavigationOnClickListener { finish() }
    }

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            tts.language = Locale.US
        }
    }

    override fun onResume() {
        super.onResume()
        val patientId = intent.getStringExtra("PATIENT_ID") ?: "p1"
        loadPatientData(patientId)
    }

    private fun loadPatientData(id: String) {
        lifecycleScope.launch {
            currentPatient = patientRepo.getPatient(id)
            currentPatient?.let { displayPatientData(it) }
        }
    }

    private fun generateAIReport() {
        val patient = currentPatient ?: return
        val meds = if (patient.medicines.isEmpty()) "None" else patient.medicines.joinToString { "${it.name} (${it.dosageMg}) at ${it.timeToTake}" }
        val vitals = "BP: ${patient.bpHistory.lastOrNull() ?: "N/A"}, Sugar: ${patient.sugarHistory.lastOrNull() ?: "N/A"}"
        
        val prompt = """
            Create a professional and high-quality Medical Health Report and Emergency First Aid Guide for ${patient.name}.
            
            PATIENT INFORMATION:
            - Age: ${patient.age}, Gender: ${patient.gender}
            - Condition: ${patient.disease}
            - Recent Vitals: $vitals
            - Medications: $meds
            
            STRICT BILINGUAL REQUIREMENT:
            Provide EVERY section in English followed immediately by its professional Kannada (ಕನ್ನಡ) translation.
            
            REQUIRED SECTIONS:
            
            1. 🏥 **HEALTH OVERVIEW / ಆರೋಗ್ಯದ ಅವಲೋಕನ**: 
               A concise summary of the patient's current health status.
            
            2. 🚑 **EMERGENCY GUIDE: DOS & DON'TS / ತುರ್ತು ಮಾರ್ಗದರ್ಶಿ: ಏನು ಮಾಡಬೇಕು ಮತ್ತು ಮಾಡಬಾರದು**:
               Provide clear, step-by-step "✅ DOs (ಮಾಡಿ)" and "❌ DON'Ts (ಮಾಡಬೇಡಿ)" for:
               - Heart Attack (ಹೃದಯಾಘಾತ)
               - Snake Bite (ಹಾವು ಕಡಿತ)
               - Poisoning (ವಿಷ ಸೇವನೆ)
            
            3. 📞 **EMERGENCY CONTACTS / ತುರ್ತು ಸಂಪರ್ಕಗಳು**:
               List 108 (Ambulance) and 112 (Police).
            
            FORMATTING RULES:
            - Use Bold text (**text**) for headers and key steps.
            - Use Emojis for sections.
            - Use clear bullet points.
            - Tone: Calm, professional, and authoritative.
            - Include the disclaimer: "This is AI-generated advice. Consult a doctor immediately in case of emergency."
        """.trimIndent()
        
        findViewById<ProgressBar>(R.id.progressBar)?.visibility = View.VISIBLE
        val tvSummary = findViewById<TextView>(R.id.tvSummary)
        tvSummary.text = "AI is analyzing data and preparing a bilingual report..."
        
        GeminiApi.getResponse(prompt) { response ->
            runOnUiThread {
                findViewById<ProgressBar>(R.id.progressBar)?.visibility = View.GONE
                lastAiResponse = response
                // Correct Markdown bold to HTML replacement
                val formattedText = response.replace(Regex("\\*\\*(.*?)\\*\\*"), "<b>$1</b>").replace("\n", "<br>")
                tvSummary.text = Html.fromHtml(formattedText, Html.FROM_HTML_MODE_COMPACT)
                showReportReadyDialog()
            }
        }
    }

    private fun showReportReadyDialog() {
        AlertDialog.Builder(this)
            .setTitle("Report Generated")
            .setMessage("Your medical report and emergency guide in English & Kannada is ready. Save as PDF or listen to it?")
            .setPositiveButton("Download PDF") { _, _ -> generatePdfReport() }
            .setNeutralButton("Voice Advice") { _, _ -> showVoiceOptions() }
            .setNegativeButton("Close", null)
            .show()
    }

    private fun showVoiceOptions() {
        val options = arrayOf("Listen in English", "Listen in Kannada (ಕನ್ನಡ)")
        AlertDialog.Builder(this)
            .setTitle("Voice Assistant")
            .setItems(options) { _, which ->
                val language = if (which == 0) Locale.US else Locale("kn", "IN")
                tts.language = language
                
                // Clean the text for TTS (remove markdown and emojis)
                val cleanText = lastAiResponse
                    .replace("**", "")
                    .replace("*", "")
                    .replace(Regex("[\\uD83C-\\uDBFF\\uDC00-\\uDFFF]+"), "")
                
                tts.speak(cleanText, TextToSpeech.QUEUE_FLUSH, null, null)
            }
            .show()
    }

    private fun generatePdfReport() {
        val patient = currentPatient ?: return
        val pdfDocument = PdfDocument()
        val pageWidth = 595
        val pageHeight = 842
        var pageNumber = 1
        
        var pageInfo = PdfDocument.PageInfo.Builder(pageWidth, pageHeight, pageNumber).create()
        var page = pdfDocument.startPage(pageInfo)
        var canvas: Canvas = page.canvas
        val paint = Paint()

        // Header
        paint.textSize = 22f
        paint.isFakeBoldText = true
        paint.color = Color.rgb(37, 99, 235) // #2563EB Blue
        canvas.drawText("Prathama Chikitse - Medical Report", 50f, 60f, paint)
        
        paint.strokeWidth = 1f
        canvas.drawLine(50f, 75f, 545f, 75f, paint)

        // Patient Details
        paint.textSize = 12f
        paint.isFakeBoldText = false
        paint.color = Color.BLACK
        var yPos = 110f
        
        paint.isFakeBoldText = true
        canvas.drawText("PATIENT DETAILS:", 50f, yPos, paint)
        paint.isFakeBoldText = false
        yPos += 20f
        canvas.drawText("Name: ${patient.name}", 50f, yPos, paint)
        yPos += 20f
        canvas.drawText("Age/Gender: ${patient.age} / ${patient.gender}", 50f, yPos, paint)
        yPos += 20f
        canvas.drawText("Condition: ${patient.disease}", 50f, yPos, paint)
        yPos += 40f
        
        // AI Report Content
        paint.isFakeBoldText = true
        paint.textSize = 14f
        paint.color = Color.rgb(30, 58, 138)
        canvas.drawText("AI GENERATED ANALYSIS & EMERGENCY GUIDE:", 50f, yPos, paint)
        paint.isFakeBoldText = false
        paint.textSize = 10f
        paint.color = Color.BLACK
        yPos += 30f

        val lines = lastAiResponse.split("\n")
        for (line in lines) {
            if (yPos > pageHeight - 60) {
                pdfDocument.finishPage(page)
                pageNumber++
                pageInfo = PdfDocument.PageInfo.Builder(pageWidth, pageHeight, pageNumber).create()
                page = pdfDocument.startPage(pageInfo)
                canvas = page.canvas
                yPos = 50f
                paint.textSize = 10f
            }
            val cleanLine = line.replace("**", "").replace("*", "").trim()
            if (cleanLine.isNotEmpty()) {
                canvas.drawText(cleanLine, 50f, yPos, paint)
                yPos += 18f
            } else {
                yPos += 10f
            }
        }

        pdfDocument.finishPage(page)

        val fileName = "${patient.name.replace(" ", "_")}_MedicalReport.pdf"
        val file = File(getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS), fileName)
        try {
            pdfDocument.writeTo(FileOutputStream(file))
            Toast.makeText(this, "Report saved as PDF: ${file.absolutePath}", Toast.LENGTH_LONG).show()
        } catch (e: Exception) {
            Toast.makeText(this, "Failed to save PDF: ${e.message}", Toast.LENGTH_SHORT).show()
        }
        pdfDocument.close()
    }

    private fun displayPatientData(patient: Patient) {
        findViewById<TextView>(R.id.tvPatientName).text = patient.name
        findViewById<TextView>(R.id.tvAgeGender).text = "Age: ${patient.age} | Blood: ${patient.bloodGroup} | Weight: ${patient.weight}"
        findViewById<TextView>(R.id.tvDisease).text = "Condition: ${patient.disease.uppercase()}"
        findViewById<TextView>(R.id.tvDoctor).text = "Doctor: ${patient.assignedDoctor}"
        findViewById<TextView>(R.id.tvEmergencyNum).text = "Emergency: ${patient.emergencyNumber}"
        findViewById<TextView>(R.id.tvCaretaker).text = "Caretaker: ${patient.careTakerName}"
        findViewById<TextView>(R.id.tvSummary).text = patient.reportSummary

        val container = findViewById<LinearLayout>(R.id.medicineContainer)
        container.removeAllViews()
        if (patient.medicines.isEmpty()) {
            val tv = TextView(this).apply { text = "No medications assigned."; setPadding(0, 8, 0, 8) }
            container.addView(tv)
        } else {
            for (med in patient.medicines) {
                val tv = TextView(this).apply {
                    text = "💊 ${med.name} (${med.dosageMg})\n   🕒 ${med.timeToTake}"
                    setPadding(0, 10, 0, 20)
                    setTextColor(Color.parseColor("#1e293b"))
                    textSize = 16f
                    typeface = Typeface.create("sans-serif-medium", Typeface.NORMAL)
                }
                container.addView(tv)
            }
        }
        
        setupChart(findViewById(R.id.bpChart), "Blood Pressure", patient.bpHistory, Color.RED)
        setupChart(findViewById(R.id.sugarChart), "Blood Sugar", patient.sugarHistory, Color.parseColor("#0ea5e9"))
    }

    private fun setupChart(chart: LineChart, label: String, data: List<Double>, color: Int) {
        if (data.isEmpty()) {
            chart.visibility = View.GONE
            return
        }
        chart.visibility = View.VISIBLE
        val entries = data.mapIndexed { index, value -> Entry(index.toFloat(), value.toFloat()) }
        val dataSet = LineDataSet(entries, label).apply {
            this.color = color
            setCircleColor(color)
            lineWidth = 3f
            circleRadius = 5f
            setDrawValues(false)
            setDrawFilled(true)
            fillAlpha = 60
            fillColor = color
            mode = LineDataSet.Mode.CUBIC_BEZIER
        }
        chart.data = LineData(dataSet)
        chart.description.isEnabled = false
        chart.xAxis.setDrawGridLines(false)
        chart.axisLeft.setDrawGridLines(true)
        chart.axisRight.isEnabled = false
        chart.animateXY(1000, 1000)
        chart.invalidate()
    }

    override fun onDestroy() {
        if (::tts.isInitialized) {
            tts.stop()
            tts.shutdown()
        }
        super.onDestroy()
    }
}
