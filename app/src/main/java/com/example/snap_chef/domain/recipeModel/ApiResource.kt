package com.example.snap_chef.domain.recipeModel

sealed class ApiResource {
    data class Recipe(val recipe:RecipeModel) : ApiResource()
    data class Invalid(val invalid:InvalidResponseModel) : ApiResource()
}