package com.example.snap_chef.presentation.home.recipe.recipeUtil

import androidx.compose.foundation.Image
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.snap_chef.domain.recipeModel.RecipeModel
import com.example.snap_chef.presentation.ui.theme.baseGreen
import com.example.snap_chef.presentation.ui.theme.poppinsFontFamily

@Composable
fun RecipeData(
    imageBitmap: ImageBitmap,
    recipe: RecipeModel
){
    Box(modifier = Modifier.fillMaxSize()) {
        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {
            item {
                Image(
                    bitmap = imageBitmap,
                    contentDescription = "Recipe Image" ,
                    modifier = Modifier
                        .rotate(90f)
                        .align(Alignment.Center)
                        .fillParentMaxWidth() ,
                    contentScale = ContentScale.Fit
                )
            }
            item { Spacer(modifier = Modifier.height(10.dp)) }
            item {
                //Recipe Heading
                Text(
                    text = recipe.foodName ,
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
                            text = recipe.cookingTime ,
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
                            text = recipe.preparationTime ,
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
                        fontFamily = poppinsFontFamily,
                        fontSize = 20.sp,
                        fontWeight = FontWeight(800)
                    )
                )
            }
            item { Spacer(modifier = Modifier.height(5.dp)) }
            itemsIndexed(items = recipe.ingredients) { i , it ->
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
                        fontFamily = poppinsFontFamily,
                        fontSize = 20.sp,
                        fontWeight = FontWeight(800)
                    )
                )
            }
            item { Spacer(modifier = Modifier.height(5.dp)) }

            itemsIndexed(items = recipe.instructions) { i , it ->
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


@Preview(showBackground = true)
@Composable
fun PreviewRecipeData(){
    val width = 300
    val height = 200
    val imageBitmap = ImageBitmap(width, height)
    RecipeData(
        imageBitmap,
        RecipeModel(
            statusCode = 500,
            foodName = "Chole Bhature",
            cookingTime = "45 mins",
            preparationTime = "30 mins",
            ingredients = listOf("Gfjkhasfkjhdkfhdkshf","dshgdjkhsakdjhaskhd", "dsajhfkjdhakfjhskfh"),
            instructions = listOf("Gfjkhasfkjhdkfhdkshf","dshgdjkhsakdjhaskhd", "dsajhfkjdhakfjhskfhggfdgdfgdf\nfdkjhfsdkfjhsdk")
        )
    )
}