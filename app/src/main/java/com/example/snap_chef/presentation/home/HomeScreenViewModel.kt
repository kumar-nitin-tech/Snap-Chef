package com.example.snap_chef.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.snap_chef.common.Constant
import com.example.snap_chef.common.Resource
import com.example.snap_chef.domain.recipeModel.SaveRecipe
import com.example.snap_chef.presentation.auth.signinPageScreen.signInEmailScreen.SignInState
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val firestore: FirebaseFirestore,
    private val signInState: SignInState
): ViewModel() {

    private val _allRecipes = MutableStateFlow<Resource<List<SaveRecipe>>>(Resource.Unspecified())
    val allRecipes = _allRecipes.asStateFlow()


    fun getAllRecipes(){
        viewModelScope.launch {
            _allRecipes.emit(Resource.Loading())
        }

        firestore.collection(Constant.COLLECTION)
            .document(firebaseAuth.uid!!)
            .collection("recipe")
            .get()
            .addOnSuccessListener {
                val recipes = it.toObjects(SaveRecipe::class.java)
                viewModelScope.launch {
                    _allRecipes.emit(Resource.Success(recipes))
                }
            }
            .addOnFailureListener {
                viewModelScope.launch {
                    _allRecipes.emit(Resource.Error(it.message.toString()))
                }
            }
    }

    fun logOut(){
        firebaseAuth.signOut()
        viewModelScope.launch {
            signInState.saveLoginState(false)
        }
    }

}