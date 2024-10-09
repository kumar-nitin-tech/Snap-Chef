package com.example.snap_chef.presentation.home.homeviewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.snap_chef.common.Constant
import com.example.snap_chef.common.Resource
import com.example.snap_chef.domain.recipeModel.SaveRecipe
import com.example.snap_chef.presentation.auth.signinPageScreen.signInEmailScreen.SignInState
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentSnapshot
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
    private var allRecipeDocument = emptyList<DocumentSnapshot>()

    private val _deleteState = MutableStateFlow<Boolean>(false)
    val deleteState = _deleteState.asStateFlow()

    init {
        getAllRecipes()
    }
    fun getAllRecipes(){
        viewModelScope.launch {
            _allRecipes.emit(Resource.Loading())
        }

        firestore.collection(Constant.COLLECTION)
            .document(firebaseAuth.uid!!)
            .collection("recipe")
            .get()
            .addOnSuccessListener {
                allRecipeDocument = it.documents
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

    fun deleteRecipe(recipe: SaveRecipe){
        val index = _allRecipes.value.data?.indexOf(recipe)
        if(index != null && index != -1){
            val documentId = allRecipeDocument[index].id
            firestore.collection(Constant.COLLECTION)
                .document(firebaseAuth.uid!!)
                .collection("recipe")
                .document(documentId)
                .delete()
                .addOnSuccessListener {
                    viewModelScope.launch {
                        _deleteState.emit(true)
                    }
                }.addOnFailureListener {
                    viewModelScope.launch {
                        _deleteState.emit(false)
                    }
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