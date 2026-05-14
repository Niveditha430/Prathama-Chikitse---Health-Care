package com.example.prathamachikitse.ai

object GeminiApi {

    fun ask(prompt: String, callback: (String) -> Unit) {

        Thread {
            try {
                // Fake response (no internet needed)
                Thread.sleep(1000)

                val result = "AI Response: $prompt"

                callback(result)

            } catch (e: Exception) {
                callback("Error: ${e.message}")
            }
        }.start()
    }
}