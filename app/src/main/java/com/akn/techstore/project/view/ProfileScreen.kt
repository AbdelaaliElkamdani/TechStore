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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.akn.techstore.R
import com.akn.techstore.project.components.OptionsList
import com.akn.techstore.project.components.ProfileHeader
import com.akn.techstore.project.components.ProfileHeaderSkeleton
import com.akn.techstore.project.theme.*
import com.akn.techstore.project.viewModel.ProfileViewModel
import com.akn.techstore.project.viewModelProvider.ProfileViewModelProviderFactory

@Composable
fun ProfileScreen(
    navController: NavController,
    onLogout : () -> Unit,
    viewModel: ProfileViewModel = viewModel(factory = ProfileViewModelProviderFactory(LocalContext.current))
) {

    // Observation de l'état de l'utilisateur
    val user by viewModel.user.observeAsState()

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
                text = stringResource(R.string.profile_title),
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                color = DarkText,
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
                if (user != null) {
                    ProfileHeader(
                        name = user!!.name,
                        email = user!!.email,
                        imageUrl = user!!.avatar
                    )
                } else {
                    ProfileHeaderSkeleton()
                }
                Spacer(Modifier.height(24.dp))
            }
            item {
                OptionsList(navController,onLogout)
            }
        }
    }
}