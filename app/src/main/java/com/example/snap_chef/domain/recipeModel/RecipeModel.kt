package com.example.snap_chef.domain.recipeModel

import com.google.gson.annotations.SerializedName

//open class StatusResponse(
//    @SerializedName("status_code")
//    open val statusCode: String
//)

data class RecipeModel(
    @SerializedName("status code")
    val statusCode: Int =0,
    @SerializedName("Food Name")
    val foodName: String ="",
    @SerializedName("Cooking Time")
    val cookingTime: String ="",
    @SerializedName("Preparation Time")
    val preparationTime: String ="",
    @SerializedName("Ingredient")
    val ingredients: List<String> = emptyList() ,
    @SerializedName("Instruction")
    val instructions: List<String> = emptyList()
)

data class InvalidResponseModel(
    val statusCode: Int ,
    val response: String
)