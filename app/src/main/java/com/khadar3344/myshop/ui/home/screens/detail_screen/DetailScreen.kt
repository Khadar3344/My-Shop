package com.khadar3344.myshop.ui.home.screens.detail_screen

import android.annotation.SuppressLint
import android.widget.Toast
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import com.khadar3344.myshop.components.CustomAppBar
import com.khadar3344.myshop.data.local.models.FavoriteProduct
import com.khadar3344.myshop.data.local.models.UserCart
import com.khadar3344.myshop.data.network.dto.Product
import com.khadar3344.myshop.ui.home.component.Error
import com.khadar3344.myshop.ui.home.component.Loading
import com.khadar3344.myshop.ui.home.component.StarRating
import com.khadar3344.myshop.ui.home.screens.cart_screen.CartViewModel
import com.khadar3344.myshop.util.Resource

@Composable
fun DetailScreen(
    viewModel: DetailViewModel = hiltViewModel(),
    cartViewModel: CartViewModel = hiltViewModel(),
    onBadgeCountChange: (Int) -> Unit,
    onBackBtnClick: () -> Unit
) {

    val detail by viewModel.details.collectAsState()

    val onAddToCartButtonClicked: (UserCart) -> Unit =
        { userCart ->
            viewModel.addToCart(userCart)
            onBadgeCountChange(cartViewModel.badgeCount.value.plus(1))
        }

    val onAddToFavoriteProduct: (FavoriteProduct) -> Unit =
        { userFavoriteCart -> viewModel.addToFavorite(userFavoriteCart) }

    ProductDetailContent(
        detail = detail,
        onBackBtnClick = onBackBtnClick,
        onAddToCartButtonClicked = onAddToCartButtonClicked,
        onAddToFavoriteButtonClicked = onAddToFavoriteProduct
    )
}

@Composable
fun ProductDetailContent(
    detail: Resource<Product>,
    onBackBtnClick: () -> Unit,
    onAddToCartButtonClicked: (UserCart) -> Unit,
    onAddToFavoriteButtonClicked: (FavoriteProduct) -> Unit
) {
    Box(modifier = Modifier.fillMaxSize()) {
        when (detail) {
            is Resource.Success ->
                SuccessScreen(
                    detail = detail.data,
                    onBackBtnClick = onBackBtnClick,
                    onAddToCartButtonClicked = onAddToCartButtonClicked,
                    onAddToFavoriteButtonClicked = onAddToFavoriteButtonClicked
                )

            Resource.Loading -> Loading()
            is Resource.Failure<*> -> Error(message = detail.exception.toString())

            else -> {}
        }
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalPagerApi::class)
@Composable
fun SuccessScreen(
    detail: Product,
    onBackBtnClick: () -> Unit,
    onAddToCartButtonClicked: (UserCart) -> Unit,
    onAddToFavoriteButtonClicked: (FavoriteProduct) -> Unit
) {

    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(15.dp)
    ) {
        CustomAppBar(
            onBackBtnClick = onBackBtnClick,
            appBarTitle = "Product Info"
        )

        Spacer(modifier = Modifier.height(30.dp))

        Card(
            modifier = Modifier.fillMaxSize(),
            shape = RoundedCornerShape(8.dp)
        ) {
            Column(
                modifier = Modifier.padding(10.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                val state = rememberPagerState(detail.images.size)
                val pageCount = detail.images.size
                HorizontalPager(
                    count = pageCount,
                    state = state,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                ) { page ->
                    AsyncImage(
                        modifier = Modifier
                            .fillMaxWidth(),
                        model = detail.images[page],
                        contentDescription = detail.title
                    )
                }
                Spacer(modifier = Modifier.height(5.dp))
                Row(
                    modifier = Modifier
                        .height(10.dp)
                        .fillMaxWidth()
                        .align(Alignment.CenterHorizontally),
                    horizontalArrangement = Arrangement.Center
                ) {
                    repeat(pageCount) { iteration ->
                        val color =
                            if (state.currentPage == iteration)
                                MaterialTheme.colorScheme.primary
                            else Color.LightGray
                        Box(
                            modifier = Modifier
                                .padding(2.dp)
                                .clip(CircleShape)
                                .size(20.dp)
                                .background(color = color)
                        )
                    }
                }
                Text(
                    text = detail.title,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    style = MaterialTheme.typography.titleMedium,
                    textAlign = TextAlign.Start
                )

                Text(
                    text = detail.description,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.Justify
                )

                Text(
                    text = "$${detail.price}",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )

                Spacer(modifier = Modifier.height(10.dp))

                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Rating",
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.padding(end = 8.dp)
                    )

                    StarRating(rating = detail.rating.toFloat())
                }


                Spacer(modifier = Modifier.height(20.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                ) {
                    Button(
                        onClick = {
                            onAddToCartButtonClicked(
                                UserCart(
                                    userId = "",
                                    productId = detail.id,
                                    quantity = 1,
                                    price = detail.price,
                                    title = detail.title,
                                    image = detail.images[0]
                                )
                            )
                            Toast.makeText(context, "Added to Shopping Carts", Toast.LENGTH_SHORT).show()
                        },
                        modifier = Modifier
                            .weight(1f)
                            .padding(end = 8.dp)
                    ) {
                        Text(text = "Add to cart")
                    }

                    Button(
                        onClick = {
                            onAddToFavoriteButtonClicked(
                                FavoriteProduct(
                                    userId = "",
                                    productId = detail.id,
                                    quantity = 1,
                                    price = detail.price,
                                    title = detail.title,
                                    image = detail.images[0]
                                )
                            )
                            Toast.makeText(context, "Added to favorites", Toast.LENGTH_SHORT).show()
                        },
                        modifier = Modifier
                            .weight(1f)
                            .padding(start = 8.dp)
                    ) {
                        Text(text = "Add to Favorites")
                    }
                }
            }
        }
    }

}

