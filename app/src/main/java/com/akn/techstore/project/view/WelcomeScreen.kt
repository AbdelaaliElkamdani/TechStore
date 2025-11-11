package com.akn.techstore.project.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.akn.techstore.R
import com.akn.techstore.project.navigation.AuthScreen

@Composable
fun WelcomeScreen(navigateTo: (AuthScreen) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.green)),
        horizontalAlignment = Alignment.CenterHorizontally

    ) {
        // En-tête (Logo et nom de la marque)
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 48.dp, bottom = 32.dp)
                .weight(1f),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Placeholder du Logo (Remplacez ceci par votre Image réelle)
            Image(
                painter = painterResource(id = R.drawable.splashscreen_logo), // Assurez-vous que votre logo est dans le dossier 'drawable'
                contentDescription = "Logo TechStore",
                modifier = Modifier
                    .size(395.dp)
            )
        }

        // Partie inférieure blanche
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f) // Prend le reste de l'espace
                .background(Color.White, RoundedCornerShape(topStart = 60.dp, topEnd = 60.dp))
                .padding(40.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Welcome",
                color = colorResource(id = R.color.black),
                fontSize = 60.sp,
                fontWeight = FontWeight.Bold,
                lineHeight = 40.sp,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            Text(
                text = "to our store",
                color = colorResource(id = R.color.black),
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                lineHeight = 40.sp,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            Text(
                text = "Future Tech. Now in your hands",
                color = Color.Gray,
                fontSize = 16.sp,
                modifier = Modifier.padding(bottom = 32.dp)
            )

            // Bouton "Get Started"
            Button(
                onClick = { navigateTo(AuthScreen.LOGIN) }, // On va à l'écran de Login
                colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.green)),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
            ) {
                Text(text = "Get Started", fontSize = 24.sp, fontWeight = FontWeight.SemiBold)
            }
        }
    }
}