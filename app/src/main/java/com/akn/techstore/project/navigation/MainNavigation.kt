package com.akn.techstore.project.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.akn.techstore.project.components.BottomNavigationBar
import com.akn.techstore.project.theme.GrayBackground
import com.akn.techstore.project.view.WelcomeScreen
import com.akn.techstore.project.viewModel.NavigationViewModel

@Composable
fun MainNavigation(
    navigationViewModel: NavigationViewModel = viewModel()
) {

    val navController = rememberNavController()
    val currentBackStack by navController.currentBackStackEntryAsState()
    val currentRoute = currentBackStack?.destination?.route
    val showBottomBar = navigationViewModel.showBottomBar
    var isStarted by rememberSaveable { mutableStateOf(false) }

    if(isStarted){
        Scaffold(
            bottomBar = {
                if (showBottomBar) {
                    val currentMainScreen = MainScreen.entries.firstOrNull {
                        it.router.route == currentRoute
                    } ?: MainScreen.HOME
                    BottomNavigationBar(
                        currentScreen = currentMainScreen,
                        navController = navController
                    )
                }
            }
        ){ paddingValues ->
            Box(
                modifier = Modifier
                    .padding(paddingValues)
                    .fillMaxSize()
                    .background(GrayBackground)
            ) {
                NavigationHost(
                    navController,
                    navigationViewModel
                )
            }
        }
    } else {
        WelcomeScreen(onGetStarted = { isStarted = true })
    }
}