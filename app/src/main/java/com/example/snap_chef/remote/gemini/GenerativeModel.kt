package com.example.snap_chef.remote.gemini

import android.graphics.Bitmap
import android.util.Log
import com.example.snap_chef.BuildConfig
import com.example.snap_chef.common.Constant
import com.google.ai.client.generativeai.GenerativeModel
import com.google.ai.client.generativeai.type.content
import com.google.ai.client.generativeai.type.generationConfig


class GeminiService(){
    private val model = GenerativeModel(
        modelName = "gemini-1.5-pro",
        apiKey = BuildConfig.apiKey ,
        generationConfig = generationConfig {
            responseMimeType = "application/json"
        }
    )

    suspend fun generateRecipe(image: Bitmap): String?{
        return try{
            val response = model.generateContent(
                content {
                    image(image)
                    text(Constant.PROMPT)
                }
            )
            response.text
        }catch (e:Exception){
            Log.e("Generate Recipe" , e.message.toString())
            null
        }
    }
}