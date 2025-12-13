package com.akn.techstore

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.*
import androidx.compose.ui.res.colorResource
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.akn.techstore.project.navigation.MainNavigation

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        setContent {
            MaterialTheme(
                colorScheme = MaterialTheme.colorScheme.copy(
                    primary = colorResource(id = R.color.green),
                    secondary = colorResource(id = R.color.green),
                    surface = colorResource(id = R.color.white),
                    background = colorResource(id = R.color.white)
                )
            ) {
                MainNavigation()
            }
        }
    }
}
