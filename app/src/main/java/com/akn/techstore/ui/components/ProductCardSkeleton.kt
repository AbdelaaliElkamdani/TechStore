package com.akn.techstore.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.akn.techstore.SkeletonColor


@Composable
fun ProductCardSkeleton(modifier: Modifier = Modifier) {
    Card(
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        modifier = modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(10.dp)) {
            Box (
                modifier = Modifier.fillMaxWidth()
            ) {
                // Squelette pour l'image (doit correspondre à la taille de l'image réelle)
                Box(
                    modifier = Modifier
                        .height(140.dp)
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(8.dp))
                        .background(SkeletonColor)
                )

                // Squelette pour le bouton J'aime (Heart) - petite forme ronde
                Box(
                    modifier = Modifier
                        .size(30.dp)
                        .padding(top = 8.dp, end = 8.dp)
                        .align(Alignment.TopEnd)
                        .clip(CircleShape) // Utiliser une forme ronde
                        .background(SkeletonColor)
                )
            }

            Spacer(Modifier.height(8.dp))

            // Squelette pour le nom du produit (texte long)
            Box(
                modifier = Modifier
                    .width(100.dp) // Largeur du bloc de texte simulé
                    .height(14.dp) // Hauteur de la ligne de texte
                    .clip(RoundedCornerShape(4.dp))
                    .background(SkeletonColor)
            )

            Spacer(Modifier.height(4.dp))

            // Squelette pour l'évaluation (rating) et le nombre de commentaires
            Row(verticalAlignment = Alignment.CenterVertically) {
                // Squelette pour l'étoile
                Box(
                    modifier = Modifier
                        .size(16.dp)
                        .clip(CircleShape)
                        .background(SkeletonColor)
                )
                Spacer(Modifier.width(4.dp))
                // Squelette pour le texte de l'évaluation
                Box(
                    modifier = Modifier
                        .width(20.dp)
                        .height(12.dp)
                        .clip(RoundedCornerShape(4.dp))
                        .background(SkeletonColor)
                )
                Spacer(Modifier.width(8.dp))
                // Squelette pour le nombre de commentaires
                Box(
                    modifier = Modifier
                        .width(40.dp)
                        .height(12.dp)
                        .clip(RoundedCornerShape(4.dp))
                        .background(SkeletonColor)
                )
            }

            Spacer(Modifier.height(4.dp))

            // Squelette pour le prix (texte gras)
            Box(
                modifier = Modifier
                    .width(40.dp) // Largeur du prix simulé
                    .height(16.dp) // Hauteur de la ligne de texte
                    .clip(RoundedCornerShape(4.dp))
                    .background(SkeletonColor)
            )
        }
    }
}
