package com.khadar3344.myshop.ui.home.component

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontWeight

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreenTopBar(
    scrollBehavior: TopAppBarScrollBehavior,
    onSearchIconClicked: () -> Unit
) {
    TopAppBar(
        scrollBehavior = scrollBehavior,
        title = { Text(text = "My Shop", fontWeight = FontWeight.Bold) },
        actions = {
            IconButton(onClick = onSearchIconClicked) {
                Icon(imageVector = Icons.Default.Search, contentDescription = "Search")
            }
        },
        colors = TopAppBarDefaults.largeTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
            actionIconContentColor = MaterialTheme.colorScheme.onPrimaryContainer
        )
    )
}