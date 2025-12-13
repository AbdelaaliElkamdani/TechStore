package com.akn.techstore.project.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
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
import com.akn.techstore.R
import com.akn.techstore.project.theme.DarkText

@Composable
fun CategoryItem(category: String, isSelected: Boolean, onCategoryClick: (String) -> Unit) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .clickable { onCategoryClick(category) }
            .clip(RoundedCornerShape(25.dp))
            .background( if (isSelected) colorResource(id = R.color.green) else colorResource(id = R.color.white))
    ) {
        Box(
            modifier = Modifier
                .height(30.dp)
                .fillMaxWidth()
                .clip(RoundedCornerShape(25.dp))
                .padding(horizontal = 16.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = category,
                fontSize = 12.sp,
                color = if(!isSelected) DarkText else colorResource(id = R.color.white),
            )
        }
    }
}