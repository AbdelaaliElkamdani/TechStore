package com.akn.techstore.project.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.akn.techstore.DarkTextColor
import com.akn.techstore.R

@Composable
fun CategoryItem(category: String) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Box(
            modifier = Modifier
                .height(30.dp)
                .clip(RoundedCornerShape(25.dp))
                .background(colorResource(id = R.color.white))
                .padding(horizontal = 16.dp)
                .clickable { /* Filtre par catégorie */ },
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = category,
                fontSize = 12.sp,
                color = DarkTextColor,
            )
        }
    }
}