package com.akn.techstore.project.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.akn.techstore.DarkTextColor
import com.akn.techstore.LightGrayBackground
import com.akn.techstore.R
import com.akn.techstore.project.components.OptionsList
import com.akn.techstore.project.components.ProfileHeader

@Composable
fun ProfileScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(LightGrayBackground)
    ) {
        Row (
            modifier = Modifier
                .fillMaxWidth()
                .background(colorResource(id = R.color.white))
        ) {

            Text(
                text = "Profile",
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                color = DarkTextColor,
                modifier = Modifier
                    .padding(bottom = 16.dp)
                    .padding(top = 16.dp)
                    .padding(start = 16.dp)
            )
        }
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(16.dp)
        ) {
            item {
                ProfileHeader(
                    name = "Emma Johnson",
                    email = "emma.johnson@email.com",
                    imageUrl = "https://placehold.co/100x100/CCCCCC/FFFFFF?text=EJ"
                )
                Spacer(Modifier.height(24.dp))
            }
            item {
                OptionsList(navController)
            }
        }
    }
}