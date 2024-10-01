package com.example.snap_chef.domain.recipeModel

import com.google.gson.Gson
import com.google.gson.JsonParser

class JsonConverter {
    private val gson = Gson()

    fun parseResponse(jsonString: String): Int{
        val parseJson = JsonParser.parseString(jsonString)
        val statusCode = parseJson.asJsonObject["status code"].asInt
        return statusCode
//        return if(statusCode == 500){
//            ApiResource.Invalid(gson.fromJson(jsonString,InvalidResponseModel::class.java))
//        }else{
//            ApiResource.Recipe(gson.fromJson(jsonString,RecipeModel::class.java))
//        }
    }
}