package com.akn.techstore.project.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.ui.graphics.vector.ImageVector

enum class MainScreen(val title: String, val icon: ImageVector, val router: Routes) {
    HOME("Discover", Icons.Default.Home, Routes.Discover),
    FAVORITES("Favorites", Icons.Default.Favorite, Routes.Favorites),
    CART("My Cart", Icons.Default.ShoppingCart, Routes.Cart),
    PROFILE("Profile", Icons.Default.Person, Routes.Profile)
}