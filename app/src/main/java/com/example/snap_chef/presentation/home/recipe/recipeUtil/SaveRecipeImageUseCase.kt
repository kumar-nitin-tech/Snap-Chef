package com.example.snap_chef.presentation.home.recipe.recipeUtil

import android.graphics.Bitmap
import com.example.snap_chef.common.Resource
import com.example.snap_chef.data.repository.RecipeRepositoryImpl
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SaveRecipeImageUseCase @Inject constructor(
    private val recipeRepositoryImpl: RecipeRepositoryImpl
) {
    operator fun invoke(bitmap: Bitmap): Flow<Resource<String>> = flow{
        emit(Resource.Loading())
        try{
            val imageUri = recipeRepositoryImpl.saveImage(bitmap)
            imageUri.onSuccess {
                emit(Resource.Success(it))
            }
            imageUri.onFailure {
                emit(Resource.Error(it.message?:"An unexpected error occurred"))
            }
        }catch (e:Exception){
            emit(Resource.Error(e.message?:"An unexpected error occurred"))
        }
    }

}