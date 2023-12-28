package com.khadar3344.myshop.ui.home.screens.favourite_screen

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.khadar3344.myshop.components.CustomAppBar
import com.khadar3344.myshop.data.local.models.FavoriteProduct
import com.khadar3344.myshop.ui.home.component.Error
import com.khadar3344.myshop.ui.home.component.Loading
import com.khadar3344.myshop.util.Resource

@Composable
fun FavouriteScreen(
    viewModel: FavoriteViewModel = hiltViewModel(),
    onProductClicked: (FavoriteProduct) -> Unit,
    onBackBtnClick: () -> Unit
) {
    val context = LocalContext.current
    val favoriteState by viewModel.favoriteCarts.collectAsState(initial = Resource.Idle)
    val onLongClicked: (FavoriteProduct) -> Unit = { favoriteProduct ->
        viewModel.deleteFavoriteItem(favoriteProduct = favoriteProduct)
        Toast.makeText(context, "Item Deleted", Toast.LENGTH_SHORT).show()
    }

    FavoriteContent(
        favoriteState = favoriteState,
        onProductClicked = onProductClicked,
        onProductLongClicked = onLongClicked,
        onBackBtnClick = onBackBtnClick
    )

}

@Composable
fun FavoriteContent(
    favoriteState: Resource<List<FavoriteProduct>>?,
    onProductClicked: (FavoriteProduct) -> Unit,
    onProductLongClicked: (FavoriteProduct) -> Unit,
    onBackBtnClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(15.dp)
    ) {
        CustomAppBar(
            onBackBtnClick = onBackBtnClick,
            appBarTitle = "Favourite"
        )
        Spacer(modifier = Modifier.height(30.dp))
        when(favoriteState) {
            Resource.Loading -> {
                Loading()
            }
            is Resource.Failure<*> -> {
                Error(message = favoriteState.exception.toString())
            }
            is Resource.Success -> {
                FavoriteList(
                    favoriteProduct = favoriteState.data,
                    onProductClicked = onProductClicked,
                    onProductLongClicked = onProductLongClicked
                )
            }

            else -> {}
        }
    }
}