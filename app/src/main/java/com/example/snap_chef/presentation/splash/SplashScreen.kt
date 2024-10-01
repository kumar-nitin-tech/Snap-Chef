package com.example.snap_chef.presentation.splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.snap_chef.R
import com.example.snap_chef.presentation.navigation.Routes
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    navController: NavHostController,
    splashViewModel: SplashViewModel = hiltViewModel()
){
    val startDestination = splashViewModel.startDestination
    LaunchedEffect(key1 = true) {
        delay(100L)
        navController.navigate(startDestination.value){
            popUpTo(Routes.SplashScreen.routes){
                inclusive = true
            }
        }
    }

    Box(
        modifier = Modifier.fillMaxSize()
    ){
        Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = "App Logo",
            modifier = Modifier
                .size(100.dp)
                .align(Alignment.Center)
        )
    }

}

@Preview(showBackground = true)
@Composable
fun PreviewSplashScreen(){
    SplashScreen(rememberNavController())
}