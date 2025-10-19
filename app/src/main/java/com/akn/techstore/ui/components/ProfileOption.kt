package com.akn.techstore.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.akn.techstore.DarkText
import com.akn.techstore.ErrorRed
import com.akn.techstore.GrayBorder
import com.akn.techstore.PrimaryGreen


@Composable
fun OptionsList() {
    Card(
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.background(Color.White)) {
            ProfileOption(
                icon = Icons.Default.Edit,
                title = "Edit Profile",
                iconColor = Color(0xFF42A5F5), // Bleu
                onClick = { /* Naviguer vers Infos */ }
            )
            HorizontalDivider(color = GrayBorder, thickness = 1.dp, modifier = Modifier.padding(horizontal = 16.dp))
            ProfileOption(
                icon = Icons.Default.Lock,
                title = "Change Password",
                iconColor = ErrorRed,
                onClick = { /* Naviguer vers Thèmes */ }
            )
            HorizontalDivider(color = GrayBorder, thickness = 1.dp, modifier = Modifier.padding(horizontal = 16.dp))
            ProfileOption(
                icon = Icons.Default.LocationOn,
                title = "Address Book",
                iconColor = Color(0xFF9C27B0), // Violet
                onClick = { /* Naviguer vers Adresses */ }
            )
            HorizontalDivider(color = GrayBorder, thickness = 1.dp, modifier = Modifier.padding(horizontal = 16.dp))
            ProfileOption(
                icon = Icons.Default.Settings,
                title = "Settings",
                iconColor = Color(0xFF607D8B), // Gris ardoise
                onClick = { /* Naviguer vers Paramètres */ }
            )
            HorizontalDivider(color = GrayBorder, thickness = 1.dp, modifier = Modifier.padding(horizontal = 16.dp))
            // Option Logout
            ProfileOption(
                icon = Icons.AutoMirrored.Filled.ExitToApp,
                title = "Logout",
                iconColor = ErrorRed,
                titleColor = ErrorRed,
                onClick = { /* Action de Déconnexion */ }
            )
        }
    }
}

@Composable
fun ProfileOption(
    icon: ImageVector,
    title: String,
    iconColor: Color,
    titleColor: Color = DarkText,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(vertical = 14.dp, horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            // Icône avec cercle de couleur
            Box(
                modifier = Modifier
                    .size(36.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(iconColor.copy(alpha = 0.1f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(icon, contentDescription = title, tint = iconColor, modifier = Modifier.size(20.dp))
            }
            Spacer(Modifier.width(16.dp))
            Text(title, fontSize = 16.sp, color = titleColor, fontWeight = FontWeight.Medium)
        }
        Icon(Icons.AutoMirrored.Filled.KeyboardArrowRight, contentDescription = "Go", tint = GrayBorder)
    }
}