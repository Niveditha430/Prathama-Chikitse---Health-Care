package com.example.prathamachikitse.dashboard

import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.prathamachikitse.R
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.latin.TextRecognizerOptions

class PatientActivity : AppCompatActivity() {

    private val CAMERA_REQ = 200
    private lateinit var resultText: TextView
    private lateinit var scanBtn: Button
    private lateinit var addBtn: Button
    private var detectedMedicine: String = ""
    private var detectedDosage: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_patient)

        resultText = findViewById(R.id.resultText)
        scanBtn = findViewById(R.id.scanBtn)
        addBtn = findViewById(R.id.addBtn)

        scanBtn.setOnClickListener {
            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            startActivityForResult(intent, CAMERA_REQ)
        }

        addBtn.setOnClickListener {
            if (detectedMedicine.isNotEmpty()) {
                // Here we would normally save to database, for now we show a success message
                Toast.makeText(this, "Saved: $detectedMedicine - $detectedDosage", Toast.LENGTH_LONG).show()
                finish()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == CAMERA_REQ && resultCode == RESULT_OK) {
            val bitmap = data?.extras?.get("data") as? Bitmap
            bitmap?.let { processImage(it) }
        }
    }

    private fun processImage(bitmap: Bitmap) {
        val image = InputImage.fromBitmap(bitmap, 0)
        val recognizer = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS)
        resultText.text = "Scanning for medicine and dosage..."

        recognizer.process(image)
            .addOnSuccessListener { visionText ->
                val lines = visionText.text.split("\n")
                if (lines.isNotEmpty()) {
                    detectedMedicine = lines[0] // Assume first line is medicine name
                    detectedDosage = if (lines.size > 1) lines[1] else "Dosage not found"
                    
                    resultText.text = "Medicine: $detectedMedicine\nDosage: $detectedDosage"
                    addBtn.isEnabled = true
                } else {
                    resultText.text = "No text found. Please scan the label clearly."
                }
            }
            .addOnFailureListener {
                resultText.text = "Error: ${it.message}"
            }
    }
}
