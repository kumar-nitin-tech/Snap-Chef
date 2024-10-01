package com.example.snap_chef.presentation.home.recipe

import android.graphics.Bitmap
import android.net.http.HttpException
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresExtension
import com.example.snap_chef.common.Resource
import com.example.snap_chef.data.repository.RecipeRepositoryImpl
import com.example.snap_chef.domain.recipeModel.ApiResource
import com.example.snap_chef.domain.recipeModel.InvalidResponseModel
import com.example.snap_chef.domain.recipeModel.JsonConverter
import com.example.snap_chef.domain.recipeModel.RecipeModel
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class RecipeUseCase @Inject constructor(
    private val recipeRepository: RecipeRepositoryImpl
) {
    private val gson = Gson()
    private val jsonConverter = JsonConverter()
    @RequiresExtension(extension = Build.VERSION_CODES.S , version = 7)
    operator fun invoke(bitmap: Bitmap):Flow<Resource<ApiResource>> = flow {
        emit(Resource.Loading())
        try {
            val response = recipeRepository.getRecipeResponse(bitmap)
            val statusCode = jsonConverter.parseResponse(response!!)
            if (statusCode == 402) {
                emit(
                    Resource.Success(
                        ApiResource.Invalid(
                            gson.fromJson(
                                response ,
                                InvalidResponseModel::class.java
                            )
                        )
                    )
                )
            } else {
                emit(
                    Resource.Success(
                        ApiResource.Recipe(
                            gson.fromJson(
                                response ,
                                RecipeModel::class.java
                            )
                        )
                    )
                )
            }

        } catch (e: HttpException) {
            Log.e("HTTP Error", e.message.toString())
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occurred"))
        }
        catch (e: Exception){
            Log.e("Google Exception", e.message.toString())
            emit(Resource.Error("An unexpected error occurred from google gemini"))
        }
    }
}