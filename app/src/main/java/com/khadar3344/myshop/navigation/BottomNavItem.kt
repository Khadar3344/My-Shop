package com.khadar3344.myshop.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomNavItem(
    val title: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val route: String
) {
    object HomeNav: BottomNavItem(
        title = "Home",
        selectedIcon = Icons.Filled.Home,
        unselectedIcon = Icons.Outlined.Home,
        route = Home.route
    )
    object FavouriteNav: BottomNavItem(
        title = "Favourite",
        selectedIcon = Icons.Filled.Favorite,
        unselectedIcon = Icons.Outlined.FavoriteBorder,
        route = Favorite.route
    )
    object CartNav: BottomNavItem(
        title = "Cart",
        selectedIcon = Icons.Filled.ShoppingCart,
        unselectedIcon = Icons.Outlined.ShoppingCart,
        route = Cart.route
    )
    object ProfileNav: BottomNavItem(
        title = "Profile",
        selectedIcon = Icons.Filled.Person,
        unselectedIcon = Icons.Outlined.Person,
        route = Profile.route
    )
}
