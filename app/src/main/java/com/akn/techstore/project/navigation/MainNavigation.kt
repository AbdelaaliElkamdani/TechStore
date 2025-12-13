package com.akn.techstore.project.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.akn.techstore.project.components.BottomNavigationBar
import com.akn.techstore.project.theme.GrayBackground
import com.akn.techstore.project.view.WelcomeScreen
import com.akn.techstore.project.viewModel.NavigationViewModel
import com.akn.techstore.project.viewModelProvider.NavigationViewModelProviderFactory

@Composable
fun MainNavigation(
    // Récupération du ViewModel via Factory (DI manuelle), garantissant l'accès au Contexte.
    navigationViewModel: NavigationViewModel = viewModel(factory = NavigationViewModelProviderFactory(
        LocalContext.current
    )
    )
) {
    // Création et mémorisation du contrôleur de navigation pour l'ensemble de l'application.
    val navController = rememberNavController()
    // Obtient l'entrée actuelle de la pile de navigation (pour connaître la route actuelle).
    val currentBackStack by navController.currentBackStackEntryAsState()
    val currentRoute = currentBackStack?.destination?.route
    // La route actuelle est utilisée pour synchroniser la barre de navigation inférieure.

    // Collecte l'état 'isStarted' du ViewModel (Statut d'onboarding terminé ou non).
    val isStarted by navigationViewModel.isStarted.collectAsState()
    // Collecte l'état 'isLoading' (pour l'affichage initial, par exemple, le chargement des préférences).
    val isLoading by navigationViewModel.isLoading.collectAsState()
    // Collecte l'état 'showBottomBar' pour afficher ou masquer la barre de navigation inférieure.
    val showBottomBar by navigationViewModel.showBottomBar.collectAsState()

    // Logique de navigation conditionnelle basée sur l'état (MVVM)
    if(!isLoading) {
        // Si le chargement initial est terminé
        if (isStarted) {
            // L'utilisateur a déjà complété l'onboarding : affiche l'écran principal
            Scaffold(
                bottomBar = {
                    if (showBottomBar) {
                        // Détermine l'écran principal actuel pour l'item sélectionné dans la barre.
                        val currentMainScreen = MainScreen.entries.firstOrNull {
                            it.router.route == currentRoute
                        } ?: MainScreen.HOME
                        // Composant affichant les icônes de navigation.
                        BottomNavigationBar(
                            currentScreen = currentMainScreen,
                            navController = navController
                        )
                    }
                }
            ) { paddingValues ->
                // Fournit l'espace pour le contenu principal, en tenant compte du padding de la Scaffold.
                Box(
                    modifier = Modifier
                        .padding(paddingValues)
                        .fillMaxSize()
                        .background(GrayBackground)
                ) {
                    // Conteneur où les écrans Compose sont échangés (le graphique de navigation).
                    NavigationHost(
                        navController,
                        navigationViewModel
                    )
                }
            }
        } else {
            // L'onboarding n'est pas terminé : affiche l'écran de bienvenue.
            WelcomeScreen(onGetStarted = {
                // Fonction de callback pour marquer l'onboarding comme complété dans le ViewModel.
                navigationViewModel.completeOnboarding()
            })
        }
    } else {
        // Affichage d'un indicateur de chargement si l'état initial n'est pas prêt.
        Box(modifier = Modifier .fillMaxSize(), contentAlignment =
            Alignment.Center) {
            CircularProgressIndicator()
        }
    }
}