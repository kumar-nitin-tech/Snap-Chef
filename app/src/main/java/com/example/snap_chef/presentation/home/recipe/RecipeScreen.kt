package com.example.snap_chef.presentation.home.recipe

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.snap_chef.domain.recipeModel.ApiResource
import com.example.snap_chef.domain.recipeModel.SaveRecipe
import com.example.snap_chef.presentation.home.recipe.recipeUtil.RecipeData
import com.example.snap_chef.presentation.home.recipe.viewmodel.ImageViewModel
import com.example.snap_chef.presentation.home.recipe.viewmodel.RecipeScreenViewModel
import com.example.snap_chef.presentation.navigation.Routes

//@RequiresExtension(extension = Build.VERSION_CODES.S , version = 7)
@Composable
fun RecipeScreen(
    imageViewModel: ImageViewModel ,
    navController: NavHostController ,
    recipeScreenViewModel: RecipeScreenViewModel = hiltViewModel()
){
    val context = LocalContext.current
    val imageBitmap by imageViewModel.imageBitmap.collectAsState()
    val recipeState = recipeScreenViewModel.recipe.collectAsState()
    val image =  recipeScreenViewModel.image.collectAsState()
    val saveRecipeState = recipeScreenViewModel.savedRecipe.collectAsState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ){
        when(recipeState.value.recipe){
            is ApiResource.Invalid -> {
                Text(
                    text = (recipeState.value.recipe as ApiResource.Invalid).invalid.response,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
            is ApiResource.Recipe -> {
                val recipeData = (recipeState.value.recipe as ApiResource.Recipe).recipe
                if(image.value.error.isNotBlank()) {
                    Toast.makeText(context,recipeState.value.error,Toast.LENGTH_SHORT).show()
                    navController.navigate(Routes.HomeScreen.routes){
                        popUpTo(Routes.RecipeScreen.routes){
                            inclusive = true
                        }
                    }
                }
                if(image.value.isLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .align(Alignment.Center)
                    )
                }
                LaunchedEffect(recipeScreenViewModel) {
                    imageBitmap?.let {
                        recipeScreenViewModel.saveImage(it)
                    }
                }
                if(image.value.imageUri.isNotBlank()){
                    val saveRecipe = SaveRecipe(recipeData,image.value.imageUri)
                    LaunchedEffect(recipeScreenViewModel) {
                        recipeScreenViewModel.saveRecipe(saveRecipe)
                    }
                    if(saveRecipeState.value.error.isNotBlank()) {
                        Toast.makeText(context,recipeState.value.error,Toast.LENGTH_SHORT).show()
                        navController.navigate(Routes.HomeScreen.routes){
                            popUpTo(Routes.RecipeScreen.routes){
                                inclusive = true
                            }
                        }
                    }
                    if(saveRecipeState.value.isLoading) {
                        CircularProgressIndicator(
                            modifier = Modifier
                                .align(Alignment.Center)
                        )
                    }
                    RecipeData(
                        imageBitmap = imageBitmap!!.asImageBitmap(),
                        recipe = saveRecipeState.value.savedRecipe.recipe
                    )
                }
            }
            null -> {
                Log.e("RecipeScreen" , recipeState.value.recipe.toString())
                imageBitmap?.let {
                    LaunchedEffect(recipeState) {
                        Log.d("Image Picked ", it.toString())
                        recipeScreenViewModel.getRecipe(it)
                    }
                }
            }
        }
        if(recipeState.value.error.isNotBlank()) {
            Toast.makeText(context,recipeState.value.error,Toast.LENGTH_SHORT).show()
            navController.navigate(Routes.HomeScreen.routes){
                popUpTo(Routes.RecipeScreen.routes){
                    inclusive = true
                }
            }
        }
        if(recipeState.value.isLoading) {
            CircularProgressIndicator(
                modifier = Modifier
                    .align(Alignment.Center)
            )
        }
    }

}


@Preview(showBackground = true)
@Composable
fun PreviewRecipeScreen(){
    RecipeScreen(viewModel(), rememberNavController())
}