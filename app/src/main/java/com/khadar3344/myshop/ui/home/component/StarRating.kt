package com.khadar3344.myshop.ui.home.component

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun StarRating(
    rating: Float,
    maxRating: Int = 5,
    size: Dp = 15.dp,
    color: Color = Color.Yellow,
    emptyStarIcon: ImageVector = Icons.Default.Star,
    filledStarIcon: ImageVector = Icons.Default.Star
) {
    Row {
        repeat(maxRating) { index ->
            val icon = if (index < rating) filledStarIcon else emptyStarIcon
            Icon(
                imageVector = icon,
                contentDescription = null, // Content description can be provided
                tint = if (index < rating) color else Color.Gray,
                modifier = Modifier.width(size)
            )
            Spacer(modifier = Modifier.width(4.dp)) // Add some space between stars
        }
    }
}