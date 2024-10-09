package com.example.snap_chef.presentation.splash

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.snap_chef.presentation.auth.signinPageScreen.signInEmailScreen.SignInState
import com.example.snap_chef.presentation.navigation.Routes
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    signInState: SignInState
): ViewModel() {

    private val _startDestination = mutableStateOf(Routes.SplashScreen.routes)
    val startDestination = _startDestination

    init {
        viewModelScope.launch {
            signInState.readLoginState().collect(){
                if(it){
                    _startDestination.value = Routes.HomeScreen.routes
                }else{
                    _startDestination.value = Routes.SignInPageScreen.routes
                }
            }
        }
    }

}