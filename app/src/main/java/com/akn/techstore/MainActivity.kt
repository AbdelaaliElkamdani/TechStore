package com.akn.techstore

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.akn.techstore.ui.navigation.AppNavigation
import com.akn.techstore.ui.navigation.AuthScreen
import com.akn.techstore.ui.navigation.BottomNavigationBar
import com.akn.techstore.ui.navigation.MainScreen
import com.akn.techstore.ui.view.CartScreen
import com.akn.techstore.ui.view.LoginScreen
import com.akn.techstore.ui.view.ProfileScreen
import com.akn.techstore.ui.view.SignUpScreen
import com.akn.techstore.ui.view.WelcomeScreen


// --- Couleurs et Constantes ---
val PrimaryColor = Color(0xFF4CAF50)
val DarkTextColor = Color(0xFF1E1E1E)
val GrayBackground = Color(0xFFF7F7F7)
val SkeletonColor = Color(0xFFE0E0E0)
val PrimaryGreen = Color(0xFF4CAF50)
val DarkText = Color(0xFF1E1E1E)
val LightGrayBackground = Color(0xFFF7F7F7)
val SoftCardBackground = Color(0xFFFFFFFF)
val ErrorRed = Color(0xFFF44336)
val GrayBorder = Color(0xFFE0E0E0)
val SkeletonHighlight = Color(0xFFC0C0C0) // Gris moyen (simule l'effet shimmer)
val TransparentGray = Color.Transparent

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        setContent {
            // État de navigation global
            var currentAuthScreen by remember { mutableStateOf(AuthScreen.WELCOME) }
            var isLoggedIn by remember { mutableStateOf(false) }

            // Thème de base pour l'application
            MaterialTheme(
                colorScheme = MaterialTheme.colorScheme.copy(
                    primary = colorResource(id = R.color.green),
                    secondary = colorResource(id = R.color.green),
                    surface = colorResource(id = R.color.white),
                    background = colorResource(id = R.color.white)
                )
            ) {
                // Fonction de navigation simple pour l'authentification
                val navigateTo: (AuthScreen) -> Unit = { screen ->
                    currentAuthScreen = screen
                }

                // Si l'utilisateur est connecté, afficher le contenu principal (MainContent)
                if (isLoggedIn) {
                    AppNavigation()
                } else {
                    // Sinon, afficher les écrans d'authentification
                    when (currentAuthScreen) {
                        AuthScreen.WELCOME -> WelcomeScreen { navigateTo(AuthScreen.LOGIN) }
                        AuthScreen.LOGIN -> LoginScreen(
                            navigateTo = navigateTo,
                            onAuthSuccess = { isLoggedIn = true } // Change l'état de connexion ici
                        )
                        AuthScreen.SIGNUP -> SignUpScreen(navigateTo = navigateTo, onAuthSuccess = { isLoggedIn = true })
                    }
                }
            }
        }
    }
}




@Preview(showBackground = true, name = "Cart Screen Preview")
@Composable
fun CartScreenPreview() {
    MaterialTheme {
        CartScreen()
    }
}

@Preview(showBackground = true, name = "Profile Screen Preview")
@Composable
fun ProfileScreenPreview() {
    MaterialTheme {
        ProfileScreen()
    }
}

@Preview(showBackground = true, name = "Profile Screen Preview")
@Composable
fun NavigationPreview() {
    MaterialTheme {
        BottomNavigationBar(currentScreen = MainScreen.HOME, onScreenSelected = {})
    }
}