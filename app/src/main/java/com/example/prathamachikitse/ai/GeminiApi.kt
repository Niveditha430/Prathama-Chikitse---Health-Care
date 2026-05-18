package com.example.prathamachikitse.ai

import android.util.Log
import com.google.ai.client.generativeai.GenerativeModel
import com.google.ai.client.generativeai.type.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

object GeminiApi {
    private const val TAG = "GeminiApi"
    
    // Ensure this API Key is valid and has 'Generative Language API' enabled in Google AI Studio.
    private const val API_KEY = "AIzaSyCdXHhzQZbyJGDs0m7DXqd4yc1uIZnVCyg"

    private val config = generationConfig {
        temperature = 0.4f
        topK = 32
        topP = 1.0f
        maxOutputTokens = 2048
    }

    // Relaxed safety settings to prevent medical information from being blocked
    private val safetySettings = listOf(
        SafetySetting(HarmCategory.HATE_SPEECH, BlockThreshold.ONLY_HIGH),
        SafetySetting(HarmCategory.HARASSMENT, BlockThreshold.ONLY_HIGH),
        SafetySetting(HarmCategory.SEXUALLY_EXPLICIT, BlockThreshold.ONLY_HIGH),
        SafetySetting(HarmCategory.DANGEROUS_CONTENT, BlockThreshold.ONLY_HIGH)
    )

    private val generativeModel = GenerativeModel(
        modelName = "gemini-1.5-flash",
        apiKey = API_KEY,
        generationConfig = config,
        safetySettings = safetySettings
    )

    private val SYSTEM_PROMPT = """
        You are 'Prathama Chikitse AI', a professional medical first-aid assistant.
        Your goal is to provide a highly structured, beautiful, and bilingual (English & Kannada) health report.
        
        STRICT RULES:
        1. Every single section must be provided in both English and Kannada (ಕನ್ನಡ).
        2. Use 🏥 for Health Overview, 🚑 for Emergency Guides.
        3. For emergency scenarios, ALWAYS provide a dedicated and extremely clear "✅ DOs (ಮಾಡಿ)" and "❌ DON'Ts (ಮಾಡಬೇಡಿ)" section for immediate actions.
        4. Use bolding (**text**) for emphasis and clear section breaks.
        5. Use emojis to make the report visually engaging and easier to read.
        6. Tone: Calm, professional, and authoritative.
        7. Always include a medical disclaimer at the end.
    """.trimIndent()

    fun getResponse(prompt: String, callback: (String) -> Unit) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                Log.d(TAG, "Initiating Gemini request with prompt: $prompt")
                val inputContent = content {
                    text("$SYSTEM_PROMPT\n\nUser Context/Request: $prompt")
                }
                
                val response = generativeModel.generateContent(inputContent)
                val responseText = response.text ?: "I am unable to process this request at the moment. Please try again with different keywords or check if the prompt is valid."
                
                withContext(Dispatchers.Main) {
                    Log.d(TAG, "Gemini Response Received")
                    callback(responseText)
                }
            } catch (e: Exception) {
                Log.e(TAG, "Gemini API Error", e)
                withContext(Dispatchers.Main) {
                    val userFriendlyError = when {
                        e.message?.contains("API_KEY_INVALID") == true -> "Error: Invalid API Key. Please check your AI Studio settings."
                        e.message?.contains("PERMISSION_DENIED") == true -> "Error: Access denied. Ensure Gemini API is enabled for this key."
                        else -> "AI Assistant Error: ${e.localizedMessage}. Please check your internet connection."
                    }
                    callback(userFriendlyError)
                }
            }
        }
    }
}
