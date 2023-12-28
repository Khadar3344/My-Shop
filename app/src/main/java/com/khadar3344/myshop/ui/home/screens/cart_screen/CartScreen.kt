package com.khadar3344.myshop.ui.home.screens.cart_screen

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.khadar3344.myshop.components.CustomAppBar
import com.khadar3344.myshop.components.CustomDefaultBtn
import com.khadar3344.myshop.data.local.models.UserCart
import com.khadar3344.myshop.ui.home.component.Error
import com.khadar3344.myshop.ui.home.component.Loading
import com.khadar3344.myshop.util.Resource
import kotlinx.coroutines.Job

@Composable
fun CartScreen(
    viewModel: CartViewModel = hiltViewModel(),
    onClickedBuyNowButton: () -> Unit,
    onCartClicked: (UserCart) -> Unit,
    onBadgeCountChange: (Int) -> Unit,
    onBackBtnClick: () -> Unit
) {

    val context = LocalContext.current
    val cartState by viewModel.userCart.collectAsState(initial = Resource.Idle)
    val onCartLongClicked = { userCart: UserCart ->
        viewModel.deleteUserCartItem(userCart = userCart)
        onBadgeCountChange(viewModel.badgeCount.value.minus(1))
        Toast.makeText(context, "Item Deleted", Toast.LENGTH_SHORT).show()
    }

    val updateTotalAmount = { cartList: List<UserCart> ->
        viewModel.updateTotalPrice(cartList = cartList)
    }

    val onDecrement = { userCart: UserCart ->
        if (userCart.quantity > 1) {
            viewModel.updateUserCartItem(
                userCart.copy(
                    quantity = userCart.quantity - 1
                )
            )
        }
    }

    val onIncrement = { userCart: UserCart ->
        viewModel.updateUserCartItem(
            userCart.copy(
                quantity = userCart.quantity + 1
            )
        )
    }

    when (cartState) {
        is Resource.Loading -> {
            Loading()
        }

        is Resource.Failure<*> -> {
            Error(message = (cartState as Resource.Failure<*>).exception.toString())
        }

        is Resource.Success -> {
            viewModel.updateTotalPrice((cartState as Resource.Success<List<UserCart>>).data)
            val totalPrice by viewModel.totalPrice.collectAsState(initial = 0.0)
            SuccessScreen(
                userCartList = (cartState as Resource.Success).data,
                onClickedBuyNowButton = onClickedBuyNowButton,
                onCartClicked = onCartClicked,
                onCartLongClicked = onCartLongClicked,
                totalPrice = totalPrice,
                onDecrement = onDecrement,
                onIncrement = onIncrement,
                updateTotalAmount = updateTotalAmount,
                onBackBtnClick = onBackBtnClick
            )
        }

        else -> {}
    }
}

@Composable
fun SuccessScreen(
    userCartList: List<UserCart>,
    onClickedBuyNowButton: () -> Unit,
    onCartClicked: (UserCart) -> Unit,
    onCartLongClicked: (UserCart) -> Unit,
    totalPrice: Double,
    onDecrement: (UserCart) -> Unit,
    onIncrement: (UserCart) -> Job,
    updateTotalAmount: (List<UserCart>) -> Job,
    onBackBtnClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(15.dp)
    ) {
        CustomAppBar(
            onBackBtnClick = onBackBtnClick,
            appBarTitle = "Shopping Cart"
        )
        Spacer(modifier = Modifier.height(30.dp))
        Box {
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(userCartList.size) { cart ->
                    CartItem(
                        userCart = userCartList[cart],
                        onCartItemClicked = onCartClicked,
                        onCartLongClicked = onCartLongClicked,
                        onIncrement = {
                            onIncrement(userCartList[cart])
                            updateTotalAmount(userCartList)
                        },
                        onDecrement = {
                            onDecrement(userCartList[cart])
                            updateTotalAmount(userCartList)
                        },
                    )
                }
            }
            Card(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth()
                    .padding(15.dp),
                elevation = CardDefaults.cardElevation(
                    defaultElevation = 10.dp
                ),
                shape = RoundedCornerShape(10.dp)
            ) {
                Column(modifier = Modifier.padding(15.dp)) {
                    Row(modifier = Modifier.fillMaxWidth()) {
                        Text(
                            text = "Total Amount",
                            style = TextStyle(
                                fontWeight = FontWeight.Bold
                            ),
                            modifier = Modifier.weight(1f)
                        )
                        Text(
                            text = "$${totalPrice}",
                            style = TextStyle(
                                fontWeight = FontWeight.Bold
                            )
                        )
                    }

                    CustomDefaultBtn(shapeSize = 50f, btnText = "Buy Now") {
                        onClickedBuyNowButton()
                    }
                }
            }
        }
    }
}