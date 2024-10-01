package com.example.snap_chef.common.utils_components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.snap_chef.R
import com.example.snap_chef.presentation.ui.theme.baseGreen
import com.example.snap_chef.presentation.ui.theme.poppinsFontFamily

@Composable
fun AuthButton(
    text : String,
    buttonColor : Color,
    textColor : Color,
    icon: Painter?,
    onClick: ()->Unit
){
    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 24.dp, end = 24.dp),
        shape = RoundedCornerShape(16.dp),
        colors = ButtonDefaults.buttonColors(buttonColor),
    )
    {
        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.CenterStart
        ) {
            if(icon != null) {
                Image(
                    painter = painterResource(id = R.drawable.google),
                    contentDescription = null,
                    modifier = Modifier
                        .size(18.dp)
                        .align(Alignment.CenterStart),
                )
            }
            Text(
                text = text,
                modifier = Modifier.align(Alignment.Center),
                style = TextStyle(
                    textAlign = TextAlign.Center,
                    fontFamily = poppinsFontFamily,
                    fontSize = 16.sp,
                    color = textColor
                )
            )
        }

    }
}

@Preview
@Composable
fun PreviewAuthButton(){
    AuthButton(
        text = "Sign In",
        buttonColor = baseGreen,
        textColor = Color.White,
        icon =painterResource(id = R.drawable.google)
    ){

    }
}