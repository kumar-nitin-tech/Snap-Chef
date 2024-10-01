package com.example.snap_chef.presentation.auth.signinPageScreen.signInEmailScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.snap_chef.common.Resource
import com.example.snap_chef.data.userValidation.RegisterFailedState
import com.example.snap_chef.data.userValidation.emailValidation
import com.example.snap_chef.data.userValidation.validatePassword
import com.example.snap_chef.data.userValidation.validation
import com.example.snap_chef.domain.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
class SignInEmailViewModel @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val signInState: SignInState
): ViewModel() {

    private val _signIn = MutableSharedFlow<Resource<FirebaseUser>>()
    val signIn = _signIn.asSharedFlow()

    private val _validation = Channel<RegisterFailedState>()
    val validation = _validation.receiveAsFlow()

    fun signIn(email: String, password: String){
        if(validation(User("",email,password))) {
            viewModelScope.launch {
                _signIn.emit(Resource.Loading())
            }
            firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnSuccessListener {
                    viewModelScope.launch {
                        it.user?.let {
                            _signIn.emit(Resource.Success(it))
                        }
                    }
                }
                .addOnFailureListener {
                    viewModelScope.launch {
                        _signIn.emit(Resource.Error(it.message.toString()))
                    }
                }
        }else{
            val signInFailedState = RegisterFailedState(
                emailValidation(email),
                validatePassword(password)
            )
            runBlocking {
                _validation.send(signInFailedState)
            }
        }
    }

    fun updateSignInState(signInSuccess:Boolean){
        viewModelScope.launch {
            signInState.saveLoginState(signInSuccess)
        }
    }
}