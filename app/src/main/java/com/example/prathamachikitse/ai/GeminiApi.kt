package com.example.prathamachikitse.ai

import com.example.prathamachikitse.BuildConfig
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import java.io.IOException


object GeminiApi {

    private val client = OkHttpClient()

    fun getResponse(prompt: String, callback: (String) -> Unit) {

        val apiKey = BuildConfig.GEMINI_API_KEY

        val json = JSONObject()
        val part = JSONObject()
        part.put("text", prompt)

        val partsArray = org.json.JSONArray()
        partsArray.put(part)

        val content = JSONObject()
        content.put("parts", partsArray)

        val contentsArray = org.json.JSONArray()
        contentsArray.put(content)

        json.put("contents", contentsArray)

        val body = json.toString()
            .toRequestBody("application/json".toMediaTypeOrNull())

        val request = Request.Builder()
            .url("https://generativelanguage.googleapis.com/v1beta/models/gemini-pro:generateContent?key=$apiKey")
            .post(body)
            .build()

        client.newCall(request).enqueue(object : Callback {

            override fun onFailure(call: Call, e: IOException) {
                callback("Error: ${e.message}")
            }

            override fun onResponse(call: Call, response: Response) {
                val responseBody = response.body?.string()

                try {
                    val jsonResponse = JSONObject(responseBody)
                    val text = jsonResponse
                        .getJSONArray("candidates")
                        .getJSONObject(0)
                        .getJSONObject("content")
                        .getJSONArray("parts")
                        .getJSONObject(0)
                        .getString("text")

                    callback(text)

                } catch (e: Exception) {
                    callback("Parsing error")
                }
            }
        })
    }
}