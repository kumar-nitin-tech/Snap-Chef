package com.example.snap_chef.presentation.home.recipe

import com.example.snap_chef.domain.recipeModel.ApiResource
import com.example.snap_chef.domain.recipeModel.SaveRecipe

data class RecipeState(
    val isLoading: Boolean = false ,
    val recipe: ApiResource? = null,
    val error: String = ""
)

data class SaveRecipeState(
    val isLoading: Boolean = false ,
    val error: String = "",
    val savedRecipe : SaveRecipe = SaveRecipe()
)

data class SaveRecipeImageState(
    val isLoading: Boolean = false ,
    val error: String = "",
    val imageUri : String = ""
)

