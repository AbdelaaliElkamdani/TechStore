package com.akn.techstore.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.akn.techstore.SkeletonColor
import com.akn.techstore.SkeletonHighlight
import com.akn.techstore.TransparentGray

@Composable
fun ProfileHeaderSkeleton(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(contentAlignment = Alignment.BottomEnd) {

            // 1. Squelette de l'Image de profil (Cercle)
            Box(
                modifier = Modifier
                    .size(100.dp)
                    .clip(CircleShape)
                    .background(SkeletonColor) // Bloc de couleur pour l'image
                    // La bordure peut être omise ou mise dans la couleur du squelette si elle doit charger aussi
                    .border(1.dp, TransparentGray, CircleShape)
            )

            // 2. Squelette du Bouton de caméra (Cercle)
            Box(
                modifier = Modifier
                    .clip(CircleShape)
                    .background(SkeletonHighlight) // Bloc de couleur pour le bouton
                    .size(30.dp)
                    .border(2.dp, TransparentGray, CircleShape)
            )
        }

        Spacer(Modifier.height(8.dp))

        // 3. Squelette du Nom d'utilisateur (Ligne de texte longue)
        Box(
            modifier = Modifier
                .width(120.dp) // Largeur du nom simulé
                .height(20.dp) // Hauteur de la ligne de texte
                .clip(RoundedCornerShape(4.dp))
                .background(SkeletonColor)
        )

        Spacer(Modifier.height(4.dp)) // Espace entre le nom et l'email

        // 4. Squelette de l'Email (Ligne de texte courte)
        Box(
            modifier = Modifier
                .width(90.dp) // Largeur de l'email simulé
                .height(14.dp) // Hauteur de la ligne de texte
                .clip(RoundedCornerShape(4.dp))
                .background(SkeletonColor)
        )
    }
}