package com.akn.techstore.project.components

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
import com.akn.techstore.project.theme.*

@Composable
fun ProfileHeaderSkeleton(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(contentAlignment = Alignment.BottomEnd) {

            Box(
                modifier = Modifier
                    .size(100.dp)
                    .clip(CircleShape)
                    .background(SkeletonColor)
                    .border(1.dp, TransparentGray, CircleShape)
            )

            Box(
                modifier = Modifier
                    .clip(CircleShape)
                    .background(SkeletonHighlight)
                    .size(30.dp)
                    .border(2.dp, TransparentGray, CircleShape)
            )
        }

        Spacer(Modifier.height(8.dp))

        Box(
            modifier = Modifier
                .width(120.dp)
                .height(20.dp)
                .clip(RoundedCornerShape(4.dp))
                .background(SkeletonColor)
        )

        Spacer(Modifier.height(4.dp))

        Box(
            modifier = Modifier
                .width(90.dp)
                .height(14.dp)
                .clip(RoundedCornerShape(4.dp))
                .background(SkeletonColor)
        )
    }
}