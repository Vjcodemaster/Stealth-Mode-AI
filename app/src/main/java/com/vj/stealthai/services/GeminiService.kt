package com.vj.stealthai.services

import android.util.Base64
import com.google.ai.client.generativeai.GenerativeModel
import com.google.ai.client.generativeai.type.Content
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File

class GeminiService(private val apiKey: String) {
    private val generativeModel by lazy {
        GenerativeModel(
            modelName = "gemini-1.5-flash", //"gemini-2.0-flash"
            apiKey = apiKey
        )
    }

    suspend fun generateResponse(prompt: String): String = withContext(Dispatchers.IO) {
        try {
            val response = generativeModel.generateContent(prompt)
            response.text ?: "No response"
        } catch (e: Exception) {
            "Error: ${e.localizedMessage}"
        }
    }

    /*suspend fun transcribeAudio(audioFile: File): String = withContext(Dispatchers.IO) {
        val base64Audio = Base64.encodeToString(audioFile.readBytes(), Base64.NO_WRAP)
        val audioPart = ContentPart.InlineData(
            mimeType = "audio/mp4",
            data = base64Audio
        )
        val request = Content(parts = listOf(audioPart))

        val response = generativeModel.generateContent(request)
        response.text ?: "No transcription result"
    }*/
}