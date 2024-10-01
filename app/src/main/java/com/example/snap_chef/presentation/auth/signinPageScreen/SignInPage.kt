package com.example.snap_chef.presentation.auth.signinPageScreen

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.snap_chef.R
import com.example.snap_chef.common.Resource
import com.example.snap_chef.common.utils_components.AuthButton
import com.example.snap_chef.presentation.auth.signinPageScreen.googleSignIn.GoogleAuthClient
import com.example.snap_chef.presentation.auth.signinPageScreen.signInViewModel.SignInPageViewModel
import com.example.snap_chef.presentation.navigation.Routes
import com.example.snap_chef.presentation.ui.theme.baseGreen
import com.example.snap_chef.presentation.ui.theme.poppinsFontFamily
import com.example.snap_chef.presentation.ui.theme.signUpButtonColor
import kotlinx.coroutines.launch

//This is the entry point for the app if the user is new
@Composable
fun SignInPageScreen(
    navController: NavHostController,
){
    val signInPageViewModel: SignInPageViewModel = hiltViewModel()
    val authState by signInPageViewModel.authState.collectAsState()
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current

    Box(
        modifier = Modifier
            .fillMaxSize(),
    ) {

        when(authState){
            is Resource.Error -> {
                Toast.makeText(context,authState.message,Toast.LENGTH_SHORT).show()
            }
            is Resource.Loading -> {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
            is Resource.Success -> {
                signInPageViewModel.updateSignInState(true)
                navController.navigate(Routes.HomeScreen.routes){
                    popUpTo(Routes.SignInPageScreen.routes){
                        inclusive = true
                    }
                }
            }
            else -> {}
        }
        Image(
            painter = painterResource(id = R.drawable.signinpagebg),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.FillBounds
        )
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(90.dp))
            Text(
                text = "Welcome to the world of recipes",
                style = TextStyle(
                    fontFamily = poppinsFontFamily,
                    color = Color.White,
                    fontSize = 20.sp,
                    fontWeight = FontWeight(500)
                )
            )
            Text(
                text = "by",
                style = TextStyle(
                    fontFamily = poppinsFontFamily,
                    color = Color.White,
                    fontSize = 20.sp,
                    fontWeight = FontWeight(500)
                )
            )
            Spacer(modifier = Modifier.height(20.dp))
            Text(
                text = "Snap-Chef",
                style = TextStyle(
                    fontFamily = poppinsFontFamily,
                    color = Color.White,
                    fontSize = 32.sp,
                    fontWeight = FontWeight(800)
                )
            )
            Spacer(modifier = Modifier.height(100.dp))
            AuthButton(
                text = "Sign In with email",
                buttonColor = baseGreen,
                textColor = Color.White,
                icon = null,
                onClick = {
                    navController.navigate(Routes.SignInScreen.routes)
                }
            )
            Spacer(modifier = Modifier.height(20.dp))
            AuthButton(
                text = "Sign In with Google",
                buttonColor = baseGreen,
                textColor = Color.White,
                icon = painterResource(id = R.drawable.google),
                onClick = {
                    coroutineScope.launch {
                        GoogleAuthClient().googleClient(context).onSuccess {credential->
                            signInPageViewModel.googleSignIn(credential)
                        }.onFailure {
                            Toast.makeText(context,it.message,Toast.LENGTH_SHORT).show()
                        }
                    }

                }
            )
            Spacer(modifier = Modifier.height(20.dp))
            Divider(Modifier.padding(start = 10.dp, end = 10.dp))
            Spacer(modifier = Modifier.height(30.dp))
            Text(
                text = "Don't have account ?",
                style = TextStyle(
                    fontFamily = poppinsFontFamily,
                    color = Color.White,
                    fontSize = 16.sp,
                    fontWeight = FontWeight(800)
                )
            )
            AuthButton(
                text = "Sign Up",
                buttonColor = signUpButtonColor,
                textColor = Color.Black,
                icon = null,
                onClick = {
                    navController.navigate(Routes.SignUpScreen.routes)
                }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewSignInPageScreen(){
    SignInPageScreen(rememberNavController())
}