package com.khadar3344.myshop.ui.home.screens.favourite_screen

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.khadar3344.myshop.data.local.models.FavoriteProduct

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun FavoriteItem(
    favoriteProduct: FavoriteProduct,
    onProductClicked: (FavoriteProduct) -> Unit,
    onProductLongClicked: (FavoriteProduct) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(15.dp)
            .combinedClickable(
                onClick = { onProductClicked(favoriteProduct) },
                onLongClick = { onProductLongClicked(favoriteProduct) }
            ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 10.dp
        ),
        shape = RoundedCornerShape(10.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            AsyncImage(
                model = favoriteProduct.image,
                contentDescription = null,
                modifier = Modifier.size(60.dp)
            )
//            Spacer(modifier = Modifier.width(8.dp))
            Column {
                Text(
                    text = favoriteProduct.title,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = "$${favoriteProduct.price}")
                Spacer(modifier = Modifier.height(4.dp))
                Text(text = "Product Id: ${favoriteProduct.productId}")
            }
//            Spacer(modifier = Modifier.width(8.dp))
            Image(
                modifier = Modifier.padding(15.dp),
                imageVector = Icons.Filled.Favorite,
                contentDescription = null
            )
        }
    }
}

@Composable
fun FavoriteList(
    favoriteProduct: List<FavoriteProduct>,
    onProductClicked: (FavoriteProduct) -> Unit,
    onProductLongClicked: (FavoriteProduct) -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {
        items(favoriteProduct.size) { index ->
            FavoriteItem(
                favoriteProduct = favoriteProduct[index],
                onProductClicked = onProductClicked,
                onProductLongClicked = onProductLongClicked
            )
        }
    }
}