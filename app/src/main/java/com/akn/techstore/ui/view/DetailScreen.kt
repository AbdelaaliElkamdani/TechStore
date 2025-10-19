package com.akn.techstore.ui.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.akn.techstore.DarkTextColor

import com.akn.techstore.R
import com.akn.techstore.ui.model.data.Product

@Composable
fun DetailScreen(product: Product, onBack: () -> Unit) {

    Column (
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.gray))
    ) {

        Row (
            modifier = Modifier
                .fillMaxWidth()
                .height(70.dp)
                .background(colorResource(id = R.color.white)),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {

            IconButton(onClick = onBack) {
                Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = DarkTextColor)
            }

            Text(
                text = "Details",
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                color = DarkTextColor
            )

            IconButton(onClick = {}) {
                Icon(
                    Icons.Default.FavoriteBorder,
                    contentDescription = "Favorite",
                    tint = Color.Gray
                )
            }
        }

        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .background(colorResource(id = R.color.gray))
                .padding(horizontal = 8.dp)
                .weight(1f),
            contentPadding = PaddingValues(top = 16.dp, bottom = 16.dp)
        ) {

            // 1. Image du Produit (Haut de l'écran)
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp)
                        .height(300.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(Color.White)
                        .padding(10.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Color.Gray)
                            .padding(8.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        // Placeholder d'image agrandi
                        Text("PRODUCT IMAGE", fontSize = 28.sp, color = Color.White)
                    }
                }
            }

            // 2. Infos principales et Couleur
            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(Color.White)
                        .padding(10.dp)
                ) {
                    // Nom et Rating
                    Row(
                        modifier = Modifier.fillMaxWidth().padding(10.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            product.name,
                            fontSize = 28.sp,
                            fontWeight = FontWeight.Bold,
                            color = DarkTextColor,
                            modifier = Modifier.weight(1f)
                        )
                        Icon(
                            Icons.Default.ThumbUp,
                            contentDescription = "Favorite",
                            tint = Color.Green,
                            modifier = Modifier.size(32.dp).padding(start = 8.dp)
                        )
                    }
                    Spacer(Modifier.height(8.dp))
                    // Rating
                    Row(
                        modifier = Modifier.fillMaxWidth().padding(10.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(Icons.Default.Star, contentDescription = "Rating", tint = Color(0xFFFFC107), modifier = Modifier.size(20.dp))
                        Text(product.rating.toString(), fontSize = 16.sp, color = DarkTextColor, fontWeight = FontWeight.Medium)
                        Spacer(Modifier.width(4.dp))
                        Text("(${product.id} reviews)", fontSize = 14.sp, color = Color.Gray)
                    }
                    Spacer(Modifier.height(8.dp))
                    Text(
                        "This is the detailed description for the ${product.name}. It features a high-resolution display, long-lasting battery life, and the latest chipset for unparalleled performance. Ideal for gaming and professional use. Don't miss out on this cutting-edge technology!",
                        fontSize = 14.sp,
                        color = Color.DarkGray,
                        lineHeight = 20.sp,
                        modifier = Modifier.fillMaxWidth().padding(10.dp),
                    )
                    Spacer(Modifier.height(8.dp))
                    // Prix
                    Text(
                        text = "$${product.price}",
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold,
                        color = DarkTextColor,
                        modifier = Modifier.fillMaxWidth().padding(10.dp),
                    )
                }
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            // Bouton Ajouter au panier
            Button(
                onClick = {
                    // Logique pour ajouter au panier
                    println("${product.name} added to cart.")
                },
                colors = ButtonDefaults.buttonColors(colorResource(id = R.color.green)),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier
                    .height(56.dp)
                    .fillMaxWidth()
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.ShoppingCart, contentDescription = "Cart", modifier = Modifier.size(20.dp).padding(end = 4.dp))
                    Text(text = "Add to Cart", fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
                }
            }
        }
    }
}