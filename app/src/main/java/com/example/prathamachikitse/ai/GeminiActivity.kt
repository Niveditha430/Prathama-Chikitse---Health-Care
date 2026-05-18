package com.example.prathamachikitse.ai

import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.text.Html
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import com.example.prathamachikitse.R
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.util.*

class GeminiActivity : AppCompatActivity(), TextToSpeech.OnInitListener {

    private lateinit var txtResponse: TextView
    private lateinit var edtPrompt: EditText
    private lateinit var btnSend: FloatingActionButton
    private lateinit var progressBar: ProgressBar
    private lateinit var tts: TextToSpeech
    private var lastResponse: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gemini)

        val toolbar = findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        toolbar.setNavigationOnClickListener { finish() }

        txtResponse = findViewById(R.id.txtResponse)
        edtPrompt = findViewById(R.id.edtPrompt)
        btnSend = findViewById(R.id.btnSend)
        progressBar = findViewById(R.id.progressBar)

        tts = TextToSpeech(this, this)

        btnSend.setOnClickListener {
            val prompt = edtPrompt.text.toString().trim()
            if (prompt.isNotEmpty()) {
                progressBar.visibility = View.VISIBLE
                txtResponse.text = "AI is thinking..."
                btnSend.isEnabled = false
                
                GeminiApi.getResponse(prompt) { response ->
                    runOnUiThread {
                        progressBar.visibility = View.GONE
                        btnSend.isEnabled = true
                        lastResponse = response
                        
                        // Formatting Markdown-style bolding to HTML
                        val formattedText = response.replace(Regex("\\*\\*(.*?)\\*\\*"), "<b>$1</b>").replace("\n", "<br>")
                        txtResponse.text = Html.fromHtml(formattedText, Html.FROM_HTML_MODE_COMPACT)
                        
                        // Scroll to bottom
                        findViewById<ScrollView>(R.id.scrollResponse).post {
                            findViewById<ScrollView>(R.id.scrollResponse).fullScroll(View.FOCUS_DOWN)
                        }
                    }
                }
            } else {
                Toast.makeText(this, "Please enter your symptoms or question", Toast.LENGTH_SHORT).show()
            }
        }

        findViewById<CardView>(R.id.cardAIResponse).setOnClickListener {
            if (lastResponse.isNotEmpty()) {
                showVoiceOptions()
            }
        }
    }

    private fun showVoiceOptions() {
        val options = arrayOf("Listen in English", "Listen in Kannada (ಕನ್ನಡ)")
        AlertDialog.Builder(this)
            .setTitle("Voice Assistant")
            .setItems(options) { _, which ->
                val language = if (which == 0) Locale.US else Locale.forLanguageTag("kn-IN")
                tts.language = language
                
                val cleanText = lastResponse
                    .replace("**", "")
                    .replace("#", "")
                    .replace("*", "")
                    .replace(Regex("[\\uD83C-\\uDBFF\\uDC00-\\uDFFF]+"), "")
                
                tts.speak(cleanText, TextToSpeech.QUEUE_FLUSH, null, null)
            }
            .show()
    }

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            tts.language = Locale.US
        }
    }

    override fun onDestroy() {
        if (::tts.isInitialized) {
            tts.stop()
            tts.shutdown()
        }
        super.onDestroy()
    }
}