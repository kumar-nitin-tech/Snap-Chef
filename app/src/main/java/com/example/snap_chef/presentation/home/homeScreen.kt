package com.example.snap_chef.presentation.home


import android.content.Intent
import android.graphics.Bitmap
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.snap_chef.R
import com.example.snap_chef.common.Resource
import com.example.snap_chef.presentation.home.recipe.ImageViewModel
import com.example.snap_chef.presentation.home.util.FabButtonItem
import com.example.snap_chef.presentation.home.util.FabButtonMain
import com.example.snap_chef.presentation.home.util.MultiFloatingButton
import com.example.snap_chef.presentation.home.util.RecipeCard
import com.example.snap_chef.presentation.home.util.SaveRecipeSharedViewModel
import com.example.snap_chef.presentation.home.util.UriToBitmap
import com.example.snap_chef.presentation.navigation.Routes

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavHostController,
    imageViewModel: ImageViewModel,
    saveRecipeSharedViewModel: SaveRecipeSharedViewModel
){

    val context = LocalContext.current
    val imageMedia = remember {
        mutableStateOf<Bitmap?>(null)
    }
    var imageSelected by remember {
        mutableStateOf(false)
    }

    val homeScreenViewModel: HomeScreenViewModel = hiltViewModel()
    LaunchedEffect(homeScreenViewModel) {
        homeScreenViewModel.getAllRecipes()
    }

    val allRecipe by homeScreenViewModel.allRecipes.collectAsState()

    val pickImage = rememberLauncherForActivityResult(ActivityResultContracts.PickVisualMedia()){uri->
        uri?.let {
            val flag = Intent.FLAG_GRANT_READ_URI_PERMISSION
            context.contentResolver.takePersistableUriPermission(uri,flag)
            imageMedia.value = UriToBitmap().uriToBitmap(context,uri)
            Log.e("Image",imageMedia.value.toString())
            imageMedia.value?.let {
                Log.e("Image",it.toString())
                imageViewModel.setImageBitmap(it)
                imageSelected = true
                navController.navigate(Routes.RecipeScreen.routes)
            }
        }
    }
    var displayMenu by remember{
        mutableStateOf(false)
    }
    val fabButtonItemList = listOf(
        FabButtonItem(
            iconRes = R.drawable.camera,
            onFabItemClicked = {
                navController.navigate(Routes.CameraScreen.routes)
            }

        ),
        FabButtonItem(
            iconRes = R.drawable.gallery,
            onFabItemClicked = {
                pickImage.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
            }
        )
    )

    Scaffold(
        Modifier.padding(10.dp),
        floatingActionButton = {
            MultiFloatingButton(
                items = fabButtonItemList,
                fabIcon = FabButtonMain()
            )
        },
        topBar = {
            TopAppBar(
                title = { /*TODO*/ },
                actions = {
                   IconButton(onClick = {
                       displayMenu = !displayMenu
                   }) {
                       Icon(imageVector = Icons.Filled.Menu, contentDescription = "menu button")
                   } 
                    
                    DropdownMenu(
                        expanded = displayMenu,
                        onDismissRequest = { displayMenu = false }
                    ) {
                        DropdownMenuItem(
                            text = { Text(text = "Logout") },
                            onClick = {
                                homeScreenViewModel.logOut()
                                navController.navigate(Routes.SignInPageScreen.routes){
                                    popUpTo(Routes.HomeScreen.routes){
                                        inclusive = true
                                    }
                                }
                            }
                        )
                    }
                },
                
            )
        }
    ) {
        Box(modifier = Modifier.padding(it)){
            LazyColumn(
                modifier = Modifier.fillMaxHeight()
            ) {
                when(allRecipe){
                    is Resource.Error -> {
                        Toast.makeText(context,allRecipe.message,Toast.LENGTH_SHORT).show()
                    }
                    is Resource.Loading -> {
                        item {
                            CircularProgressIndicator(
                                modifier = Modifier.align(Alignment.Center)
                            )
                        }
                    }
                    is Resource.Success -> {

                        items(allRecipe.data!!){recipe->

                            RecipeCard(saveRecipe = recipe, onCardClick = {
                                saveRecipeSharedViewModel.saveSharedRecipe(recipe)

                                navController.navigate(Routes.SavedRecipeScreen.routes)
                            })
                            Spacer(modifier = Modifier.height(10.dp))
                        }
                    }
                    else -> {}
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewHomeScreen(){
    HomeScreen(rememberNavController(), viewModel(), viewModel())
}
