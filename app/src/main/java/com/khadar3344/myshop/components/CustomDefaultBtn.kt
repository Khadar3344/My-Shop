package com.khadar3344.myshop.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CustomDefaultBtn(
    shapeSize: Float,
    btnText: String,
    onClick: () -> Unit
) {
    Button(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 20.dp, bottom = 20.dp)
            .height(50.dp)
            .clip(RoundedCornerShape(shapeSize.dp)),
        onClick = {
            onClick()
        },
        colors = ButtonDefaults.buttonColors(
            contentColor = Color.White
        ),
    ) {
        Text(text = btnText, fontSize = 16.sp)
    }
}

@Preview(showBackground = true)
@Composable
fun CustomBtnPreview() {
    CustomDefaultBtn(shapeSize = 50f, btnText = "Save") {
        
    }
}