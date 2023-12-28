package com.khadar3344.myshop.navigation

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.khadar3344.myshop.ui.auth.screens.ForgotPasswordScreen
import com.khadar3344.myshop.ui.auth.screens.LoginScreen
import com.khadar3344.myshop.ui.auth.screens.SignUpScreen
import com.khadar3344.myshop.ui.home.screens.cart_screen.CartScreen
import com.khadar3344.myshop.ui.home.screens.dashboard_screen.DashboardScreen
import com.khadar3344.myshop.ui.home.screens.dashboard_screen.DashboardViewModel
import com.khadar3344.myshop.ui.home.screens.detail_screen.DetailScreen
import com.khadar3344.myshop.ui.home.screens.favourite_screen.FavouriteScreen
import com.khadar3344.myshop.ui.home.screens.profile_screen.ProfileScreen
import com.khadar3344.myshop.ui.payment.PaymentScreen

@Composable
fun AppNavHost(
    navHostController: NavHostController,
    modifier: Modifier = Modifier,
    onBadgeCountChange: (Int) -> Unit
) {
    val context = LocalContext.current
    NavHost(
        navController = navHostController,
        startDestination = SignIn.route,
        modifier = modifier
    ) {
        composable(route = SignIn.route) {
            LoginScreen(navController = navHostController)
        }

        composable(route = SignUp.route) {
            SignUpScreen(navController = navHostController)
        }

        composable(route = ForgotPass.route) {
            ForgotPasswordScreen(navController = navHostController)
        }

        composable(route = Home.route) {
            val viewModel: DashboardViewModel = hiltViewModel()
            DashboardScreen(
                state = viewModel.state,
                onEvent = viewModel::onEvent,
                onProductClicked = {
                    val route = "${ProductDetail.route}/${it.id}"
                    navHostController.navigate(route = route)
                }
            )
        }

        composable(
            route = ProductDetail.routeWithArgs,
            arguments = ProductDetail.arguments
        ) {
            DetailScreen(
                onBadgeCountChange = onBadgeCountChange,
                onBackBtnClick = { navHostController.popBackStack() }
            )
        }

        composable(Favorite.route) {
            FavouriteScreen(
                onProductClicked = {
                    val route = "${ProductDetail.route}/${it.productId}"
                    navHostController.navigate(route = route)
                },
                onBackBtnClick = { navHostController.popBackStack() }
            )
        }
        composable(Cart.route) {
            CartScreen(
                onClickedBuyNowButton = {
                    navHostController.navigate(Payment.route)
                },
                onCartClicked = {
                    val route = "${ProductDetail.route}/${it.productId}"
                    navHostController.navigate(route = route)
                },
                onBadgeCountChange = onBadgeCountChange,
                onBackBtnClick = { navHostController.popBackStack() }
            )
        }
        composable(Profile.route) {
            ProfileScreen(
                logout = {
                    navHostController.navigate(SignIn.route) {
                        popUpTo(SignIn.route) {
                            inclusive = true
                        }
                    }
                    Toast.makeText(context, "Logout", Toast.LENGTH_SHORT).show()
                },
                onBackBtnClick = { navHostController.popBackStack() }
            )
        }
        composable(Payment.route) {
            PaymentScreen {
                navHostController.popBackStack()
            }
        }
    }
}