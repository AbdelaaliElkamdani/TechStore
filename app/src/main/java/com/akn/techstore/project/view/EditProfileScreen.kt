package com.akn.techstore.project.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.akn.techstore.DarkTextColor
import com.akn.techstore.LightGrayBackground
import com.akn.techstore.PrimaryGreen
import com.akn.techstore.R

@Composable
fun EditProfileScreen(navController: NavController) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(LightGrayBackground)
    ) {
        Row (
            modifier = Modifier
                .fillMaxWidth()
                .background(colorResource(id = R.color.white)),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            IconButton(
                onClick = { navController.popBackStack() },
                modifier = Modifier.padding(16.dp)
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back",
                    tint = DarkTextColor
                )
            }

            Text(
                text = "Edit Profile",
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                color = DarkTextColor,
                modifier = Modifier
                    .padding(bottom = 16.dp)
                    .padding(top = 16.dp)
                    .padding(start = 16.dp)
            )
        }

        // Liste des articles du panier
        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

        }

        // Bouton de Checkout (Paiement)
        Button(
            onClick = { /* Action de paiement */ },
            colors = ButtonDefaults.buttonColors(containerColor = PrimaryGreen),
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .height(56.dp)
        ) {
            Text(
                text = "Conferm",
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}