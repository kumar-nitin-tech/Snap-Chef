package com.example.snap_chef.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.snap_chef.presentation.auth.signinPageScreen.SignInPageScreen
import com.example.snap_chef.presentation.auth.signinPageScreen.signInEmailScreen.SignInEmailScreen
import com.example.snap_chef.presentation.auth.signupScreen.SignUpScreen
import com.example.snap_chef.presentation.home.HomeScreen
import com.example.snap_chef.presentation.home.camera.CameraScreen
import com.example.snap_chef.presentation.home.recipe.viewmodel.ImageViewModel
import com.example.snap_chef.presentation.home.recipe.RecipeScreen
import com.example.snap_chef.presentation.home.recipe.state.SavedRecipeScreen
import com.example.snap_chef.presentation.home.util.SaveRecipeSharedViewModel
import com.example.snap_chef.presentation.splash.SplashScreen

@Composable
fun NavGraph (
    navController: NavHostController
){
    val imageViewModel = viewModel<ImageViewModel>()
    val saveRecipeSharedViewModel = viewModel<SaveRecipeSharedViewModel>()
    NavHost(
        navController = navController,
        startDestination = Routes.SplashScreen.routes
    ){
        composable(Routes.SignInPageScreen.routes){
            SignInPageScreen(navController)
        }
        composable(Routes.SignUpScreen.routes){
            SignUpScreen(navController)
        }
        composable(Routes.SignInScreen.routes){
            SignInEmailScreen(navController)
        }
        composable(Routes.HomeScreen.routes){
            HomeScreen(navController,imageViewModel,saveRecipeSharedViewModel)
        }
        composable(Routes.SplashScreen.routes){
            SplashScreen(navController)
        }

        composable(Routes.CameraScreen.routes) {
            CameraScreen(navController , imageViewModel)
        }
        composable(
            Routes.RecipeScreen.routes
        ) {
            RecipeScreen(imageViewModel , navController)
        }

        composable(Routes.SavedRecipeScreen.routes){
            SavedRecipeScreen(saveRecipeSharedViewModel)
        }
    }
}