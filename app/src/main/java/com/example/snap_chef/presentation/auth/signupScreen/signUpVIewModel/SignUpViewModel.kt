package com.example.snap_chef.presentation.auth.signupScreen.signUpVIewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.snap_chef.common.Constant
import com.example.snap_chef.common.Resource
import com.example.snap_chef.data.userValidation.RegisterFailedState
import com.example.snap_chef.data.userValidation.emailValidation
import com.example.snap_chef.data.userValidation.validatePassword
import com.example.snap_chef.data.userValidation.validation
import com.example.snap_chef.domain.model.User
import com.example.snap_chef.presentation.auth.signinPageScreen.signInEmailScreen.SignInState
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val fireBaseAuth: FirebaseAuth,
    private val db: FirebaseFirestore,
    private val signInState: SignInState
) : ViewModel(){
    private val _signUp = MutableStateFlow<Resource<User>>(Resource.Unspecified());
    val signUp = _signUp.asStateFlow()

    private val _validation = Channel<RegisterFailedState>()
    val validation = _validation.receiveAsFlow()

    fun createAccount(user: User){
        viewModelScope.launch {
            _signUp.emit(Resource.Loading())
        }
        if(validation(user)){
            fireBaseAuth.createUserWithEmailAndPassword(user.email, user.password)
                .addOnSuccessListener {result->
                    result?.let {
                        saveUserInfo(it.user!!.uid, user)
                    }
                    viewModelScope.launch {
                        _signUp.emit(Resource.Success(user))
                    }

                }
                .addOnFailureListener {
                    viewModelScope.launch {
                        _signUp.emit(Resource.Error(it.message.toString()))
                    }
                }
        }else{
            val signUpFailedState = RegisterFailedState(
                emailValidation(user.email),
                validatePassword(user.password)
            )
            runBlocking {
                _validation.send(signUpFailedState)
            }
        }
    }

    private fun saveUserInfo(userUID:String, user: User){
        db.collection(Constant.COLLECTION)
            .document(userUID)
            .set(user)
            .addOnSuccessListener {
                viewModelScope.launch {
                    _signUp.emit(Resource.Success(user))
                }
            }
            .addOnFailureListener{
                viewModelScope.launch {
                    _signUp.emit(Resource.Error(it.message.toString()))
                }
            }
    }

    fun updateLoginState(signUpSuccess: Boolean){
        viewModelScope.launch {
            signInState.saveLoginState(signUpSuccess)
        }
    }
}