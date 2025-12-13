package com.akn.techstore.project.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.akn.techstore.project.navigation.MainScreen
import com.akn.techstore.project.theme.PrimaryColor
import com.akn.techstore.project.viewModel.CartViewModel
import com.akn.techstore.project.viewModel.FavouriteViewModel

@Composable
fun BottomNavigationBar(
    currentScreen: MainScreen,
    navController: NavController
) {
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
                    onClick = { navController.navigate(screen.router.route) },
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