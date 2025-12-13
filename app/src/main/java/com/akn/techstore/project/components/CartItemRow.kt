package com.akn.techstore.project.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.akn.techstore.project.model.data.Cart
import com.akn.techstore.project.theme.*

@Composable
fun CartItemRow(
    item: Cart,
    onQuantityChange: (Int) -> Unit,
    onRemove: () -> Unit
) {
    Card(
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Box (
            modifier = Modifier.background(Color.White)
        ) {
            Row(
                modifier = Modifier.padding(12.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.weight(1f)) {
                    Box(
                        modifier = Modifier
                            .size(80.dp)
                            .clip(RoundedCornerShape(8.dp)),
                        contentAlignment = Alignment.Center
                    ) {
                        AsyncImage(
                            model = item.product.imageUrl,
                            contentDescription = "Product Image",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .fillMaxSize()
                        )
                    }

                    Spacer(Modifier.width(12.dp))

                    Column(modifier = Modifier.weight(1f)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.Top
                        ) {
                            Text(
                                item.product.name,
                                fontWeight = FontWeight.SemiBold,
                                color = DarkText,
                                fontSize = 16.sp,
                                modifier = Modifier.weight(1f)
                            )
                        }
                        if (item.product.color.isNotEmpty()) {
                            Text("(${item.product.color})", fontSize = 14.sp, color = Color.Gray)
                        }
                        Text(
                            "${"%.2f".format(item.product.price)}Dh",
                            fontWeight = FontWeight.Bold,
                            color = PrimaryGreen,
                            fontSize = 15.sp
                        )
                    }
                }

                Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(start = 12.dp)) {
                    QuantityButton(Icons.AutoMirrored.Filled.KeyboardArrowLeft, enabled = item.quantity > 1) {
                        onQuantityChange(item.quantity - 1)
                    }
                    Text(item.quantity.toString(), fontSize = 16.sp, fontWeight = FontWeight.SemiBold, modifier = Modifier.padding(horizontal = 8.dp))
                    QuantityButton(Icons.AutoMirrored.Filled.KeyboardArrowRight) {
                        onQuantityChange(item.quantity + 1)
                    }

                }
            }

            Icon(
                Icons.Default.Close,
                contentDescription = "Remove",
                tint = Color.Gray,
                modifier = Modifier
                    .clickable(onClick = onRemove)
                    .padding(top = 8.dp, end = 8.dp)
                    .align(Alignment.TopEnd)
                    .size(20.dp)
            )
        }
    }
}