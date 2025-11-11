package com.akn.techstore.project.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.akn.techstore.GrayBackground
import com.akn.techstore.project.components.BottomNavigationBar
import com.akn.techstore.project.view.AddressScreen
import com.akn.techstore.project.view.ChangePasswordScreen
import com.akn.techstore.project.view.SettingScreen
import com.akn.techstore.project.view.CartScreen
import com.akn.techstore.project.view.DetailScreen
import com.akn.techstore.project.view.DiscoverScreen
import com.akn.techstore.project.view.EditProfileScreen
import com.akn.techstore.project.view.FavouritesScreen
import com.akn.techstore.project.view.ProfileScreen


enum class MainScreen(val title: String, val icon: ImageVector, val router: Routes) {
    HOME("Discover", Icons.Default.Home, Routes.Discover),
    FAVORITES("Favorites", Icons.Default.Favorite, Routes.Favorites),
    CART("My Cart", Icons.Default.ShoppingCart, Routes.Cart),
    PROFILE("Profile", Icons.Default.Person, Routes.Profile)
}

@Composable
fun AppNavigation() {

    val navController = rememberNavController()
    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = currentBackStackEntry?.destination?.route
    val mainRoutes = MainScreen.entries.map { it.router.route }

    Scaffold(
        bottomBar = {
            if (currentRoute in mainRoutes) {
                val currentMainScreen = MainScreen.entries.firstOrNull { it.router.route == currentRoute }?: MainScreen.HOME
                BottomNavigationBar(
                    currentScreen = currentMainScreen,
                    navController = navController
                )
            }
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .background(GrayBackground)
        ) {
            NavHost(
                navController = navController,
                startDestination = Routes.Discover.route
            ) {
                composable(Routes.Discover.route) {
                    DiscoverScreen(navController)
                }
                composable(Routes.Favorites.route) {
                    FavouritesScreen(navController)
                }
                composable(Routes.Cart.route) {
                    CartScreen()
                }
                composable(Routes.Profile.route) {
                    ProfileScreen(navController)
                }
                composable(
                    route = Routes.Detail.route,
                    arguments = listOf(
                        navArgument("id") {
                            type = NavType.IntType
                        }
                    )
                ) { backStackEntry ->
                    DetailScreen(
                        productId = backStackEntry.arguments?.getInt("id") ?: -1,
                        onBack = { navController.popBackStack() }
                    )
                }
                composable(Routes.EditProfile.route) {
                    EditProfileScreen(navController)
                }
                composable(Routes.Password.route) {
                    ChangePasswordScreen()
                }
                composable(Routes.Address.route) {
                    AddressScreen()
                }
                composable(Routes.Setting.route) {
                    SettingScreen()
                }
            }
        }
    }
}