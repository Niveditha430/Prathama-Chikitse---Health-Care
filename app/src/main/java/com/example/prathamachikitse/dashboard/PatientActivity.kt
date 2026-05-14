package com.example.prathamachikitse.dashboard

import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.latin.TextRecognizerOptions

class PatientActivity : AppCompatActivity() {

    private val CAMERA_REQ = 200

    private lateinit var resultText: TextView
    private lateinit var scanBtn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val layout = LinearLayout(this)
        layout.orientation = LinearLayout.VERTICAL
        layout.setPadding(30, 30, 30, 30)

        val title = TextView(this)
        title.text = "💊 Medicine Scanner"
        title.textSize = 20f

        scanBtn = Button(this)
        scanBtn.text = "📸 Scan Medicine"

        resultText = TextView(this)
        resultText.text = "Detected medicine will appear here"
        resultText.textSize = 16f

        scanBtn.setOnClickListener {
            try {
                val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                startActivityForResult(intent, CAMERA_REQ)
            } catch (e: Exception) {
                Toast.makeText(this, "Camera not available", Toast.LENGTH_SHORT).show()
            }
        }

        layout.addView(title)
        layout.addView(scanBtn)
        layout.addView(resultText)

        setContentView(layout)
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        data: Intent?
    ) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == CAMERA_REQ && resultCode == RESULT_OK) {

            val bitmap = data?.extras?.get("data") as? Bitmap

            if (bitmap != null) {
                processImage(bitmap)
            } else {
                Toast.makeText(this, "Image capture failed", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun processImage(bitmap: Bitmap) {

        val image = InputImage.fromBitmap(bitmap, 0)
        val recognizer = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS)

        resultText.text = "Scanning..."

        recognizer.process(image)
            .addOnSuccessListener { visionText ->

                val detectedText = visionText.text

                if (detectedText.isEmpty()) {
                    resultText.text = "No text found"
                } else {
                    resultText.text = "Detected:\n\n$detectedText"
                }
            }
            .addOnFailureListener {
                resultText.text = "Scan failed"
            }
    }
}