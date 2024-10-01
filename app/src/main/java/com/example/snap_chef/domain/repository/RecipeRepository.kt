package com.example.snap_chef.domain.repository

import android.graphics.Bitmap
import com.example.snap_chef.domain.recipeModel.SaveRecipe

interface RecipeRepository {
    suspend fun getRecipeResponse(bitmap: Bitmap) : String?
    suspend fun saveImage(bitmap: Bitmap): Result<String>
    suspend fun saveRecipe(recipe: SaveRecipe): Result<SaveRecipe>
}