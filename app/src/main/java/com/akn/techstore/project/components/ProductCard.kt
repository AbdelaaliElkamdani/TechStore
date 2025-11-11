package com.akn.techstore.project.components

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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
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
import com.akn.techstore.project.model.data.Product

@Composable
fun ProductCard(product: Product, modifier: Modifier = Modifier) {
    Card(
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        modifier = modifier
            .fillMaxWidth(),
        colors = CardDefaults.cardColors(Color.White),
    ) {
        Column(modifier = Modifier.background(Color.White)) {
            Column(modifier = Modifier.padding(10.dp)) {
                Box {
                    Box(
                        modifier = Modifier
                            .height(140.dp)
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(8.dp))
                            .background(Color(0xFFE0E0E0))

                    ) {
                        Text("IMG", modifier = Modifier.align(Alignment.Center), color = Color.Gray)
                    }

                    Icon(
                        Icons.Default.FavoriteBorder,
                        contentDescription = "Favorite",
                        tint = Color.Gray,
                        modifier = Modifier
                            .size(30.dp)
                            .padding(top = 8.dp, end = 8.dp)
                            .align(Alignment.TopEnd)
                    )
                }
                Spacer(Modifier.height(8.dp))
                Text(
                    product.name,
                    fontWeight = FontWeight.SemiBold,
                    color = DarkTextColor,
                    fontSize = 14.sp
                )
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        Icons.Default.Star,
                        contentDescription = "Rating",
                        tint = Color(0xFFFFC107),
                        modifier = Modifier.size(16.dp)
                    )
                    Text(product.rating.toString(), fontSize = 12.sp, color = Color.Gray)
                    Spacer(Modifier.width(8.dp))
                    Text("(${product.id} reviews)", fontSize = 12.sp, color = Color.Gray)
                }
                Spacer(Modifier.height(4.dp))
                Text(
                    "$${product.price}",
                    fontWeight = FontWeight.Bold,
                    color = colorResource(id = R.color.green),
                    fontSize = 16.sp
                )
            }
        }
    }
}