package com.example.snap_chef.presentation.home.util

import androidx.lifecycle.ViewModel
import com.example.snap_chef.domain.recipeModel.SaveRecipe
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class SaveRecipeSharedViewModel(): ViewModel() {

    private val _sharedRecipe = MutableStateFlow(SaveRecipe())
    val sharedRecipe = _sharedRecipe.asStateFlow()

    fun saveSharedRecipe(recipe: SaveRecipe) {
        _sharedRecipe.value = recipe
    }
}