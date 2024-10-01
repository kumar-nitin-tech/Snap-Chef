package com.example.snap_chef.data.repository

import android.graphics.Bitmap
import android.util.Log
import com.example.snap_chef.common.Constant
import com.example.snap_chef.domain.recipeModel.SaveRecipe
import com.example.snap_chef.domain.repository.RecipeRepository
import com.example.snap_chef.remote.gemini.GeminiService
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.storage
import kotlinx.coroutines.tasks.await
import java.io.ByteArrayOutputStream
import java.util.UUID
import javax.inject.Inject

class RecipeRepositoryImpl @Inject constructor(
    private val geminiService: GeminiService,
    private val firestore: FirebaseFirestore,
    private val auth: FirebaseAuth
) : RecipeRepository{
    override suspend fun getRecipeResponse(bitmap: Bitmap) : String?{
        return geminiService.generateRecipe(bitmap)
    }

    override suspend fun saveImage(bitmap: Bitmap): Result<String>{
        return try {
            val storage = Firebase.storage
            val imageRef = storage.reference.child("recipeImage/image/${UUID.randomUUID()}")
            val byteArray = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArray)
            val data = byteArray.toByteArray()
            val uploadTask = imageRef.putBytes(data).await()
            val downloadUrl = uploadTask.storage.downloadUrl.await().toString()
            Log.d("RecipeRepository", "Image uploaded successfully: $downloadUrl")
            Result.success(downloadUrl)
        } catch (e: Exception) {
            Log.e("RecipeRepository", "Error uploading image", e)
            Result.failure(e)
        }
    }

    override suspend fun saveRecipe(recipe: SaveRecipe): Result<SaveRecipe> {
        return try {
            firestore.collection(Constant.COLLECTION).document(auth.uid!!)
                .collection("recipe")
                .document()
                .set(recipe)
                .await()
            Result.success(recipe)
        } catch (e: Exception) {
            Log.e("RecipeRepository", "Error getting recipe", e)
            Result.failure(e)
        }
    }

}