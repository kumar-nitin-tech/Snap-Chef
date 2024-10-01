package com.example.snap_chef.presentation.home.recipe

import android.graphics.Bitmap
import android.os.Build
import android.os.ext.SdkExtensions
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.snap_chef.common.Resource
import com.example.snap_chef.domain.recipeModel.SaveRecipe
import com.example.snap_chef.presentation.home.recipe.recipeUtil.SaveRecipeImageUseCase
import com.example.snap_chef.presentation.home.recipe.recipeUtil.SaveRecipeUseCase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class RecipeScreenViewModel @Inject constructor(
    private val recipeUseCase: RecipeUseCase,
    private val saveRecipeImageUseCase: SaveRecipeImageUseCase,
    private val saveRecipeUseCase: SaveRecipeUseCase,
    private val firestore: FirebaseFirestore,
    private val firebaseAuth: FirebaseAuth
): ViewModel() {
    private val _recipe = MutableStateFlow(RecipeState())
    val recipe= _recipe.asStateFlow()

    private val _image = MutableStateFlow(SaveRecipeImageState())
    val image = _image.asStateFlow()

    private val _savedRecipe = MutableStateFlow(SaveRecipeState())
    val savedRecipe= _savedRecipe.asStateFlow()

    fun getRecipe(bitmap: Bitmap){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R && SdkExtensions.getExtensionVersion(
                Build.VERSION_CODES.S) >= 7) {
            recipeUseCase(bitmap).onEach {
                when(it){
                    is Resource.Error -> {
                        _recipe.value = RecipeState(error = it.message ?: "Unexpected Error Occurred")
                    }
                    is Resource.Loading -> {
                        _recipe.value = RecipeState(isLoading = true)
                    }
                    is Resource.Success -> {
                        _recipe.value = RecipeState(recipe = it.data)
                    }
                    else -> {}
                }
            }.launchIn(viewModelScope)
        }
    }



     fun saveImage(bitmap: Bitmap){
        saveRecipeImageUseCase(bitmap).onEach {
            when(it){
                is Resource.Error -> {
                    _image.value = SaveRecipeImageState(error = it.message ?: "Unexpected Error Occurred")
                }
                is Resource.Loading -> {
                    _image.value = SaveRecipeImageState(isLoading = true)
                }
                is Resource.Success -> {
                    _image.value = SaveRecipeImageState(imageUri = it.data!!)
                }
                else -> {}
            }
        }.launchIn(viewModelScope)
    }

    fun saveRecipe(saveRecipe: SaveRecipe){
        saveRecipeUseCase(saveRecipe).onEach {
            when(it){
                is Resource.Error -> {
                    _savedRecipe.value = SaveRecipeState(error = it.message ?: "Unexpected Error Occurred")
                }
                is Resource.Loading -> {
                    _savedRecipe.value = SaveRecipeState(isLoading = true)
                }
                is Resource.Success -> {
                    _savedRecipe.value = SaveRecipeState(savedRecipe = it.data!!)
                }
                else -> {}
            }
        }.launchIn(viewModelScope)
    }
}