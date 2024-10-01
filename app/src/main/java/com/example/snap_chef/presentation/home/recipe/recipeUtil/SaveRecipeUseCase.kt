package com.example.snap_chef.presentation.home.recipe.recipeUtil

import com.example.snap_chef.common.Resource
import com.example.snap_chef.data.repository.RecipeRepositoryImpl
import com.example.snap_chef.domain.recipeModel.SaveRecipe
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SaveRecipeUseCase @Inject constructor(
    private val recipeRepository: RecipeRepositoryImpl
) {
    operator fun invoke(recipe: SaveRecipe): Flow<Resource<SaveRecipe>> = flow{
        emit(Resource.Loading())
        try{
            val response = recipeRepository.saveRecipe(recipe)
            response.onSuccess {
                emit(Resource.Success(it))
            }
            response.onFailure {
                emit(Resource.Error(it.message ?: "An unexpected error occurred"))
            }
        }catch (e:Exception){
            emit(Resource.Error(e.message?:"An unexpected error occurred"))
        }
    }

}