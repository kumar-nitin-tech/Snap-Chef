package com.example.snap_chef.domain.recipeModel


data class SaveRecipe(
    val recipe: RecipeModel = RecipeModel(),
    val imageUri: String = ""
)