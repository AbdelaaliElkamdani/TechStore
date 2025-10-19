package com.akn.techstore.ui.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.sp
import com.akn.techstore.GrayBackground
import com.akn.techstore.PrimaryColor
import com.akn.techstore.ui.model.data.Product
import com.akn.techstore.ui.view.CartScreen
import com.akn.techstore.ui.view.DetailScreen
import com.akn.techstore.ui.view.DiscoverScreen
import com.akn.techstore.ui.view.FavouritesScreen
import com.akn.techstore.ui.view.PlaceholderScreen
import com.akn.techstore.ui.view.ProfileScreen


enum class MainScreen(val title: String, val icon: ImageVector) {
    HOME("Discover", Icons.Default.Home),
    FAVORITES("Favorites", Icons.Default.Favorite),
    CART("My Cart", Icons.Default.ShoppingCart),
    PROFILE("Profile", Icons.Default.Person)
}

@Composable
fun BottomNavigationBar(currentScreen: MainScreen, onScreenSelected: (MainScreen) -> Unit) {
    Column(modifier = Modifier.background(Color.White)) {
        NavigationBar(
            containerColor = Color.Transparent,
            contentColor = PrimaryColor
        ) {
            MainScreen.entries.forEach { screen ->
                val isSelected = currentScreen == screen
                NavigationBarItem(
                    icon = {
                        Icon(
                            imageVector = screen.icon,
                            contentDescription = screen.title
                        )
                    },
                    label = { Text(screen.title, fontSize = 10.sp) },
                    selected = isSelected,
                    onClick = { onScreenSelected(screen) },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = PrimaryColor,
                        selectedTextColor = PrimaryColor,
                        unselectedIconColor = Color.Gray,
                        unselectedTextColor = Color.Gray,
                        indicatorColor = Color.Transparent
                    )
                )
            }
        }
    }
}

@Composable
fun AppNavigation() {
    // Nouvelle variable pour gérer l'état de navigation détaillée (hors barre de nav principale)
    // Permet de naviguer vers DetailScreen ou CartScreen
    var currentMainScreen by remember { mutableStateOf<Any>(MainScreen.HOME) }

    // Conversion de l'état pour la Bottom Bar
    val currentBottomScreen = when (currentMainScreen) {
        is MainScreen -> currentMainScreen as MainScreen
        else -> MainScreen.HOME // Si on est sur un écran détail, on met en surbrillance l'accueil
    }

    // Fonction de navigation pour l'accueil/panier/favoris/profil
    val navigateToMain: (MainScreen) -> Unit = { screen ->
        currentMainScreen = screen
    }

    // Fonction pour naviguer vers un produit spécifique (sera utilisé plus tard)
    val navigateToDetails: (Product) -> Unit = { product ->
        // Pour gérer la navigation vers l'écran de détail, nous mettons l'objet Product dans l'état de navigation
        currentMainScreen = product
        // Note: La Bottom Bar ne s'affichera pas si currentMainScreen n'est pas de type MainScreen
    }

    Scaffold(
        bottomBar = {
            // La bottom bar s'affiche uniquement sur les écrans principaux
            if (currentMainScreen is MainScreen) {
                BottomNavigationBar(
                    currentScreen = currentBottomScreen,
                    onScreenSelected = navigateToMain
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
            when (currentMainScreen) {
                MainScreen.HOME -> DiscoverScreen(navigateToDetails = navigateToDetails)
                MainScreen.FAVORITES -> FavouritesScreen(navigateToDetails = navigateToDetails)
                MainScreen.CART -> CartScreen()
                MainScreen.PROFILE -> ProfileScreen()
                is Product -> DetailScreen(
                    product = currentMainScreen as Product,
                    onBack = { currentMainScreen = MainScreen.HOME }
                )

                else -> PlaceholderScreen("Unknown Screen")
            }
        }
    }
}