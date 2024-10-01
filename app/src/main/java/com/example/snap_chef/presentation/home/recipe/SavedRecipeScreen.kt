package com.example.snap_chef.presentation.home.recipe

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.snap_chef.presentation.home.util.SaveRecipeSharedViewModel
import com.example.snap_chef.presentation.ui.theme.baseGreen
import com.example.snap_chef.presentation.ui.theme.poppinsFontFamily

@Composable
fun SavedRecipeScreen(
    saveRecipeSharedViewModel: SaveRecipeSharedViewModel
){
    val saveRecipe by saveRecipeSharedViewModel.sharedRecipe.collectAsState()

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {
            item {
                AsyncImage(
                    model = saveRecipe.imageUri,
                    contentDescription = "Recipe Image" ,
                    modifier = Modifier
                        .align(Alignment.Center)
                        .fillParentMaxWidth() ,
                    contentScale = ContentScale.Fit
                )
            }
            item { Spacer(modifier = Modifier.height(10.dp)) }
            item {
                //Recipe Heading
                Text(
                    text = saveRecipe.recipe.foodName ,
                    style = TextStyle(
                        fontSize = 24.sp ,
                        fontFamily = poppinsFontFamily ,
                        fontWeight = FontWeight(800)
                    )
                )
            }
            item { Spacer(modifier = Modifier.height(10.dp)) }
            item {
                Row(
                    modifier = Modifier.fillMaxWidth() ,
                    horizontalArrangement = Arrangement.Absolute.SpaceBetween
                ) {
                    Column(
                        modifier = Modifier
                            .wrapContentSize()
                            .clip(RoundedCornerShape(16.dp))
                            .background(color = baseGreen)
                            .padding(5.dp) ,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "Cooking Time" ,
                            style = TextStyle(
                                fontSize = 14.sp ,
                                fontFamily = poppinsFontFamily ,
                            )
                        )
                        Text(
                            text = saveRecipe.recipe.cookingTime ,
                            style = TextStyle(
                                fontSize = 12.sp ,
                                fontFamily = poppinsFontFamily ,
                            )
                        )
                    }

                    Column(
                        modifier = Modifier
                            .wrapContentSize()
                            .clip(RoundedCornerShape(16.dp))
                            .background(color = baseGreen)
                            .padding(5.dp) ,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "Preparation Time" ,
                            style = TextStyle(
                                fontSize = 14.sp ,
                                fontFamily = poppinsFontFamily ,
                            )
                        )
                        Text(
                            text = saveRecipe.recipe.preparationTime ,
                            style = TextStyle(
                                fontSize = 12.sp ,
                                fontFamily = poppinsFontFamily ,
                            )
                        )
                    }
                }

            }
            item { Spacer(modifier = Modifier.height(10.dp)) }
            item {
                Text(
                    text = "Ingredients : ",
                    style = TextStyle(
                        fontFamily = poppinsFontFamily ,
                        fontSize = 20.sp,
                        fontWeight = FontWeight(800)
                    )
                )
            }
            item { Spacer(modifier = Modifier.height(5.dp)) }
            itemsIndexed(items = saveRecipe.recipe.ingredients) { i , it ->
                Row(
                    horizontalArrangement = Arrangement.spacedBy(2.dp)
                ) {
                    Text(
                        text = "${i + 1}." ,
                        modifier = Modifier.padding(top = 3.dp),
                        style = TextStyle(
                            fontSize = 16.sp ,
                            fontFamily = poppinsFontFamily ,
                        )
                    )
                    Text(
                        text = it ,
                        modifier = Modifier.padding(top = 3.dp),
                        style = TextStyle(
                            fontSize = 16.sp ,
                            fontFamily = poppinsFontFamily ,
                        )
                    )
                }

            }
            item { Spacer(modifier = Modifier.height(10.dp)) }
            item {
                Text(
                    text = "Instructions : ",
                    style = TextStyle(
                        fontFamily = poppinsFontFamily ,
                        fontSize = 20.sp,
                        fontWeight = FontWeight(800)
                    )
                )
            }
            item { Spacer(modifier = Modifier.height(5.dp)) }

            itemsIndexed(items = saveRecipe.recipe.instructions) { i , it ->
                Row(
                    horizontalArrangement = Arrangement.spacedBy(2.dp)
                ) {
                    Text(
                        text = "${i + 1}." ,
                        modifier = Modifier.padding(top = 3.dp),
                        style = TextStyle(
                            fontSize = 16.sp ,
                            fontFamily = poppinsFontFamily ,
                        )
                    )
                    Text(
                        text = it ,
                        modifier = Modifier.padding(top = 3.dp),
                        style = TextStyle(
                            fontSize = 16.sp ,
                            fontFamily = poppinsFontFamily ,
                        )
                    )
                }
            }

        }
    }

}