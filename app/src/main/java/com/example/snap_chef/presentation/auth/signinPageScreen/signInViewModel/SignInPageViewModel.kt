package com.example.snap_chef.presentation.auth.signinPageScreen.signInViewModel

import androidx.credentials.Credential
import androidx.credentials.CustomCredential
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.snap_chef.common.Constant
import com.example.snap_chef.common.Resource
import com.example.snap_chef.domain.model.GoogleUser
import com.example.snap_chef.presentation.auth.signinPageScreen.signInEmailScreen.SignInState
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential.Companion.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignInPageViewModel @Inject constructor(
    private val auth : FirebaseAuth ,
    private val signInState : SignInState ,
    private val db: FirebaseFirestore ,
) : ViewModel() {
    private val _authState = MutableStateFlow<Resource<GoogleUser>>(Resource.Unspecified())
    val authState = _authState.asStateFlow()

    fun googleSignIn(credential: Credential){
        viewModelScope.launch {
            _authState.emit(Resource.Loading())
        }
        try{
            if(credential is CustomCredential && credential.type == TYPE_GOOGLE_ID_TOKEN_CREDENTIAL){
                val googleIdToken = GoogleIdTokenCredential.createFrom(credential.data)
                val firebaseCredential = GoogleAuthProvider.getCredential(googleIdToken.idToken,null)
                auth.signInWithCredential(firebaseCredential).addOnSuccessListener {
                    it.user?.let {user->
                        saveGoogleUserData(auth.uid!!,user)
                    }
                }.addOnFailureListener {
                    viewModelScope.launch {
                        _authState.emit(Resource.Error(it.message.toString()))
                    }
                }
            }else{
                viewModelScope.launch {
                    _authState.emit(Resource.Error("Invalid Credential"))
                }
            }
        }catch (e:Exception){
            viewModelScope.launch {
                _authState.emit(Resource.Error(e.message.toString()))
            }
        }

    }


    private fun saveGoogleUserData(userUID: String , user: FirebaseUser){
        val googleUser = GoogleUser(
            uid = user.uid,
            displayName = user.displayName,
            email = user.email
        )
        viewModelScope.launch {
            _authState.emit(Resource.Loading())
        }
        db.collection(Constant.COLLECTION)
            .document(userUID)
            .set(googleUser)
            .addOnSuccessListener {
                viewModelScope.launch {
                    _authState.emit(Resource.Success(googleUser))
                }
            }
            .addOnFailureListener{
                viewModelScope.launch {
                    _authState.emit(Resource.Error(it.message.toString()))
                }
            }

    }

    fun updateSignInState(signInSuccess:Boolean){
        viewModelScope.launch {
            signInState.saveLoginState(signInSuccess)
        }
    }
}