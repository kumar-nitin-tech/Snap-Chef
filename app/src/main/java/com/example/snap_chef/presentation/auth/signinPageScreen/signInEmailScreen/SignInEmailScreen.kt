package com.example.snap_chef.presentation.auth.signinPageScreen.signInEmailScreen

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.snap_chef.R
import com.example.snap_chef.common.Resource
import com.example.snap_chef.common.utils_components.AuthButton
import com.example.snap_chef.data.userValidation.RegisterValidation
import com.example.snap_chef.presentation.navigation.Routes
import com.example.snap_chef.presentation.ui.theme.baseGreen
import com.example.snap_chef.presentation.ui.theme.poppinsFontFamily
import kotlinx.coroutines.launch

@Composable
fun SignInEmailScreen(
    navController: NavHostController,
    signInEmailViewModel: SignInEmailViewModel = hiltViewModel()
){
    val context = LocalContext.current
    var isLoading by remember {
        mutableStateOf(false)
    }
    
    var emailError by remember {
        mutableStateOf("")
    }
    
    var passwordError by remember {
        mutableStateOf("")
    }
    
    LaunchedEffect(signInEmailViewModel.validation) {
        signInEmailViewModel.validation.collect {
            if(it.email is RegisterValidation.Failed){
                emailError = it.email.message
            }
            if(it.password is RegisterValidation.Failed){
                passwordError = it.password.message
            }
        }
    }
    
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        if(isLoading){
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        }
        Column(
            modifier = Modifier.align(Alignment.TopStart)
        ) {
            Spacer(modifier = Modifier.height(100.dp))
            Text(
                text = "Welcome Back",
                style = TextStyle(
                    fontFamily = poppinsFontFamily,
                    color = Color.Black,
                    fontSize = 25.sp,
                    fontWeight = FontWeight(500)
                ),
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
            Spacer(modifier = Modifier.height(60.dp))
            var emailTextInput by remember {
                mutableStateOf("")
            }

            TextField(
                value = emailTextInput,
                onValueChange = { emailTextInput = it },
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .fillMaxWidth()
                    .padding(start = 20.dp, end = 20.dp),
                shape = RoundedCornerShape(16.dp),
                colors = TextFieldDefaults.colors(
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                ),
                isError = emailError.isNotEmpty(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                supportingText = {
                    if (emailError.isNotEmpty()) {
                        Text(text = emailError, color = Color.Red)
                    }
                },
                label = {
                    Text(
                        text = "Email",
                        style = TextStyle(
                            fontFamily = poppinsFontFamily,
                            color = Color.Gray,
                            fontSize = 16.sp,
                            fontWeight = FontWeight(500)
                        ),
                    )
                }

            )
            Spacer(modifier = Modifier.height(25.dp))
            var passwordTextInput by remember {
                mutableStateOf("")
            }

            TextField(
                value = passwordTextInput,
                onValueChange = { passwordTextInput = it },
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .fillMaxWidth()
                    .padding(start = 20.dp, end = 20.dp),
                shape = RoundedCornerShape(16.dp),
                colors = TextFieldDefaults.colors(
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                ),
                label = {
                    Text(
                        text = "Password",
                        style = TextStyle(
                            fontFamily = poppinsFontFamily,
                            color = Color.Gray,
                            fontSize = 16.sp,
                            fontWeight = FontWeight(500)
                        ),
                    )
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                isError = passwordError.isNotEmpty(),
                visualTransformation = PasswordVisualTransformation(),
                supportingText = {
                    if(passwordError.isNotEmpty()){
                        Text(text = passwordError, color = Color.Red)
                    }
                }
            )
            Spacer(modifier = Modifier.height(25.dp))
            AuthButton(
                text = "Sign In",
                buttonColor = baseGreen,
                textColor = Color.White,
                icon = null,
                onClick = {
                    signInEmailViewModel.signIn(emailTextInput, passwordTextInput)
                    signInEmailViewModel.viewModelScope.launch {
                        signInEmailViewModel.signIn.collect {
                            when (it) {
                                is Resource.Error -> {
                                    Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
                                }

                                is Resource.Loading -> {
                                    isLoading = true
                                }

                                is Resource.Success -> {
                                    signInEmailViewModel.updateSignInState(true)
                                    navController.navigate(Routes.HomeScreen.routes) {
                                        popUpTo(Routes.SignInPageScreen.routes) {
                                            inclusive = true
                                        }
                                    }
                                }

                                else -> Unit
                            }
                        }
                    }
                }
            )

            Text(
                text = "OR",
                style = TextStyle(
                    fontFamily = poppinsFontFamily,
                    color = Color.Black,
                    fontSize = 20.sp,
                    fontWeight = FontWeight(500)
                ),
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )

            AuthButton(
                text = "Sign In with Google",
                buttonColor = baseGreen,
                textColor = Color.White,
                icon = painterResource(id = R.drawable.google),
                onClick = {

                }
            )
            Spacer(modifier = Modifier.height(10.dp))
            Row(
                modifier = Modifier.padding(start = 20.dp),
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    text = "Don't have account ?",
                    style = TextStyle(
                        fontFamily = poppinsFontFamily,
                        color = Color.Black,
                        fontSize = 15.sp,
                        fontWeight = FontWeight(500)
                    )
                )

                Text(
                    text = "Sign Up",
                    style = TextStyle(
                        fontFamily = poppinsFontFamily,
                        color = baseGreen,
                        fontSize = 15.sp,
                        fontWeight = FontWeight(500)
                    ),
                    modifier = Modifier.clickable {
                        navController.navigate(Routes.SignUpScreen.routes) {
                            popUpTo(Routes.SignInScreen.routes) {
                                inclusive = true
                            }
                        }
                    }
                )
            }

        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewSignInEmailScreen(){
    SignInEmailScreen(rememberNavController())
}