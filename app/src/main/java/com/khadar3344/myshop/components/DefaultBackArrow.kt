package com.khadar3344.myshop.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.khadar3344.myshop.R

@Composable
fun DefaultBackArrow(
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .size(30.dp)
            .clip(CircleShape)
            .clickable {
                onClick()
            },
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.back_icon),
            contentDescription = "Arrow Back",
            modifier = Modifier.size(20.dp)
        )
    }
}