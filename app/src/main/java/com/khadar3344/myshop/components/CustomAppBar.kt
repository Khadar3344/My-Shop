package com.khadar3344.myshop.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

@Composable
fun CustomAppBar(
    onBackBtnClick: () -> Unit,
    appBarTitle: String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(modifier = Modifier.weight(0.5f)) {
            DefaultBackArrow {
                onBackBtnClick()
            }
        }
        Box(modifier = Modifier.weight(0.7f)) {
            Text(
                text = appBarTitle,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}