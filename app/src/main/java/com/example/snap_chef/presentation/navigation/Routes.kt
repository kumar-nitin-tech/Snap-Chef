package com.example.snap_chef.presentation.navigation

sealed class Routes(val routes: String) {
    data object SignInPageScreen: Routes("signInPageScreen")
    data object SignInScreen: Routes("signInScreen")
    data object SignUpScreen: Routes("signUpScreen")
    data object HomeScreen: Routes("homeScreen")
    data object SplashScreen: Routes("splashScreen")
    data object CameraScreen: Routes("cameraScreen")
    data object RecipeScreen: Routes("recipeScreen")
    data object SavedRecipeScreen: Routes("savedRecipeScreen")

}