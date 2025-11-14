package com.akn.techstore.project.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.akn.techstore.project.theme.*


@Composable
fun SummaryRow(label: String, amount: Double, color: Color, isTotal: Boolean = false) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            label,
            color = DarkText,
            fontSize = if (isTotal) 18.sp else 15.sp,
            fontWeight = if (isTotal) FontWeight.Bold else FontWeight.Normal
        )
        Text(
            (if (amount >= 0) "$" else "-$") + "%.2f".format(kotlin.math.abs(amount)),
            color = color,
            fontSize = if (isTotal) 19.sp else 15.sp,
            fontWeight = if (isTotal) FontWeight.ExtraBold else FontWeight.SemiBold
        )
    }
}

@Composable
fun PriceSummary(subtotal: Double, deliveryFee: Double, discount: Double, total: Double) {
    Card(
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
        modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp)
    ) {
        Column(modifier = Modifier.background(Color.White)) {
            Column(modifier = Modifier.padding(16.dp)) {
                SummaryRow("Subtotal", subtotal, Color.Black)
                SummaryRow("Delivery Fee", deliveryFee, Color.Black)
                SummaryRow("Discount (40%)", -discount, ErrorRed)

                Spacer(Modifier.height(12.dp))
                HorizontalDivider(color = GrayBorder)
                Spacer(Modifier.height(12.dp))

                SummaryRow("Total", total, PrimaryGreen, isTotal = true)
            }
        }
    }
}