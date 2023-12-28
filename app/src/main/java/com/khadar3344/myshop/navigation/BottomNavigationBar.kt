package com.khadar3344.myshop.navigation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomNavigationBar(
    badgeState: Int,
    navController: NavController,
    bottomBarState: MutableState<Boolean>
) {
    val navItemList = listOf(
        BottomNavItem.HomeNav,
        BottomNavItem.FavouriteNav,
        BottomNavItem.CartNav,
        BottomNavItem.ProfileNav
    )

    AnimatedVisibility(
        visible = bottomBarState.value,
        enter = slideInVertically(initialOffsetY = { it }),
        exit = slideOutVertically(targetOffsetY = { it }),
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        NavigationBar {
            navItemList.forEach { screen ->
                NavigationBarItem(
                    selected = navBackStackEntry?.destination?.route == screen.route,
                    onClick = {
                        navController.navigate(screen.route)
                    },
                    label = { Text(text = screen.title) },
                    alwaysShowLabel = false,
                    icon = {
                        BadgedBox(
                            badge = {
                                if (screen == BottomNavItem.CartNav) {
                                    if (badgeState != 0) {
                                        Badge {
                                            Text(text = "$badgeState")
                                        }
                                    }
                                }
                            }
                        ) {
                            Icon(
                                imageVector = if (navBackStackEntry?.destination?.route == screen.route)
                                    screen.selectedIcon
                                else screen.unselectedIcon,
                                contentDescription = screen.title
                            )
                        }
                    }

                )
            }
        }
    }
}