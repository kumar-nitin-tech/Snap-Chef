package com.example.snap_chef.presentation.home.util

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.snap_chef.domain.recipeModel.RecipeModel
import com.example.snap_chef.domain.recipeModel.SaveRecipe
import com.example.snap_chef.presentation.ui.theme.baseGreen
import com.example.snap_chef.presentation.ui.theme.poppinsFontFamily

@Composable
fun RecipeCard(
    saveRecipe: SaveRecipe,
    onCardClick: () -> Unit
){

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(120.dp) ,
        border = BorderStroke(2.dp , Color.Black),
        shape = RoundedCornerShape(16.dp),
        onClick = {
            onCardClick()
        }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            AsyncImage(
                model =  saveRecipe.imageUri,
                contentDescription =  "Food Item Image",
                contentScale = ContentScale.Fit,
                modifier = Modifier.size(120.dp)
            )
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(top = 5.dp, start = 5.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp),

            ) {
                Text(
                    text = saveRecipe.recipe.foodName,
                    style = TextStyle(
                        fontFamily = poppinsFontFamily,
                        fontSize = 20.sp,
                        fontWeight = FontWeight(800)
                    )
                )

                Row(
                    modifier =  Modifier
                        .fillMaxWidth()
                        .padding(start = 5.dp, end = 15.dp),
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
                                fontSize = 12.sp ,
                                fontFamily = poppinsFontFamily ,
                            )
                        )
                        Text(
                            text = saveRecipe.recipe.cookingTime ,
                            style = TextStyle(
                                fontSize = 10.sp ,
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
                                fontSize = 12.sp ,
                                fontFamily = poppinsFontFamily ,
                            )
                        )
                        Text(
                            text = saveRecipe.recipe.preparationTime ,
                            style = TextStyle(
                                fontSize = 10.sp ,
                                fontFamily = poppinsFontFamily ,
                            )
                        )
                    }
                }
            }
        }
    }

}


@Preview(showBackground = true)
@Composable
fun PreviewRecipeCard(){
    RecipeCard(SaveRecipe(
        RecipeModel(
            foodName = "chole",
            cookingTime = "30 mins",
            preparationTime = "30 mins",
            statusCode = 200
        ),
        "https://www.google.com/imgres?q=chole&imgurl=https%3A%2F%2Fwww.indianhealthyrecipes.com%2Fwp-content%2Fuploads%2F2023%2F08%2Fchole-recipe.jpg&imgrefurl=https%3A%2F%2Fwww.indianhealthyrecipes.com%2Fchole%2F&docid=OjAoMRdAxP3AvM&tbnid=q13RpWWksMGunM&vet=12ahUKEwjGhfK1ouuIAxXfUPUHHWpWADoQM3oECBoQAA..i&w=1200&h=1200&hcb=2&ved=2ahUKEwjGhfK1ouuIAxXfUPUHHWpWADoQM3oECBoQAA"
    ),
        {})
}