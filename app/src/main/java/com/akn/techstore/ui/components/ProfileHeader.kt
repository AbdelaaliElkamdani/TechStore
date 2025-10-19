package com.akn.techstore.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.akn.techstore.DarkText
import com.akn.techstore.GrayBorder
import com.akn.techstore.PrimaryGreen
import com.akn.techstore.SoftCardBackground

@Composable
fun ProfileHeader(name: String, email: String, imageUrl: String) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(contentAlignment = Alignment.BottomEnd) {
            // Image de profil
            Box(
                modifier = Modifier
                    .size(100.dp)
                    .clip(CircleShape)
                    .background(Color(0xFFE0E0E0)) // Couleur de fond du placeholder
                    .border(1.dp, GrayBorder, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Text(name.first().toString(), fontSize = 40.sp, color = Color.Gray)
            }
            // Bouton de caméra (Modification)
            Box(
                modifier = Modifier
                    .clip(CircleShape)
                    .background(PrimaryGreen)
                    .size(30.dp)
                    .border(2.dp, SoftCardBackground, CircleShape)
                    .clickable { /* Action Modifier Image */ },
                contentAlignment = Alignment.Center
            ) {
                Icon(Icons.Default.Edit, contentDescription = "Edit Picture", tint = Color.White, modifier = Modifier.size(16.dp))
            }
        }
        Spacer(Modifier.height(8.dp))
        Text(name, fontWeight = FontWeight.Bold, fontSize = 20.sp, color = DarkText)
        Text(email, fontSize = 14.sp, color = Color.Gray)
    }
}