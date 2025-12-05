package com.akn.techstore.project.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.akn.techstore.project.view.AddressScreen
import com.akn.techstore.project.view.CartScreen
import com.akn.techstore.project.view.ChangePasswordScreen
import com.akn.techstore.project.view.DetailScreen
import com.akn.techstore.project.view.DiscoverScreen
import com.akn.techstore.project.view.EditProfileScreen
import com.akn.techstore.project.view.FavouritesScreen
import com.akn.techstore.project.view.LoginScreen
import com.akn.techstore.project.view.ProfileScreen
import com.akn.techstore.project.view.SettingScreen
import com.akn.techstore.project.view.SignUpScreen
import com.akn.techstore.project.viewModel.NavigationViewModel

@Composable
fun NavigationHost(
    navController: NavHostController,
    navigationViewModel: NavigationViewModel
) {
    val isLoggedIn = navigationViewModel.isLoggedIn

    NavHost(
        navController = navController,
        startDestination = if (isLoggedIn) {
            Routes.Discover.route
        } else {
                Routes.Login.route
        }
    ) {
        composable(Routes.Login.route) {
            navigationViewModel.disableBottomBar()
            LoginScreen(
                onNavigateToRegister = { navController.navigate(Routes.Register.route) },
                onLoginSuccess = {
                    navigationViewModel.loggedIn()
                    navController.navigate(Routes.Discover.route) {
                        popUpTo(0) { inclusive = true }
                    }
                }
            )
        }
        composable(Routes.Register.route) {
            navigationViewModel.disableBottomBar()
            SignUpScreen(
                navController = navController,
                onSignUpSuccess = {
                    navigationViewModel.loggedIn()
                    navController.navigate(Routes.Discover.route) {
                        popUpTo(0) { inclusive = true }
                    }
                }
            )
        }

        //---App routes---//

        composable(Routes.Discover.route) {
            navigationViewModel.enableBottomBar()
            DiscoverScreen(navController)
        }
        composable(Routes.Favorites.route) {
            navigationViewModel.enableBottomBar()
            FavouritesScreen(navController)
        }
        composable(Routes.Cart.route) {
            navigationViewModel.enableBottomBar()
            CartScreen()
        }
        composable(Routes.Profile.route) {
            navigationViewModel.enableBottomBar()
            ProfileScreen(
                navController,
                onLogout = {
                    navigationViewModel.loggedOut()
                    navController.navigate(Routes.Login.route) {
                        popUpTo(Routes.Discover.route) { inclusive = true }
                    }
                }
            )
        }

        //---other routes---//
        composable(
            route = Routes.Detail.route,
            arguments = listOf(
                navArgument("id") {
                    type = NavType.IntType
                }
            )
        ) { backStackEntry ->
            navigationViewModel.disableBottomBar()
            DetailScreen(
                productId = backStackEntry.arguments?.getInt("id") ?: -1,
                onBack = { navController.popBackStack() }
            )
        }
        composable(Routes.EditProfile.route) {
            navigationViewModel.disableBottomBar()
            EditProfileScreen(navController)
        }
        composable(Routes.Password.route) {
            navigationViewModel.disableBottomBar()
            ChangePasswordScreen()
        }
        composable(Routes.Address.route) {
            navigationViewModel.disableBottomBar()
            AddressScreen()
        }
        composable(Routes.Setting.route) {
            navigationViewModel.disableBottomBar()
            SettingScreen()
        }
    }
}