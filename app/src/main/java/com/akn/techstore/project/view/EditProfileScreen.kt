package com.akn.techstore.project.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.akn.techstore.R
import com.akn.techstore.project.components.ProfileHeader
import com.akn.techstore.project.components.ProfileHeaderSkeleton
import com.akn.techstore.project.theme.*
import com.akn.techstore.project.viewModel.ProfileViewModel
import com.akn.techstore.project.viewModelProvider.ProfileViewModelProviderFactory

@Composable
fun EditProfileScreen(
    navController: NavController,
    viewModel : ProfileViewModel = viewModel(factory = ProfileViewModelProviderFactory(LocalContext.current))
) {

    // Observation de l'état de l'utilisateur (LiveData)
    val user by viewModel.user.observeAsState()

    // État local pour les champs de saisie (initialisation à vide)
    var nameField by remember { mutableStateOf("") }
    var emailField by remember { mutableStateOf("") }

    // État pour le chargement ou le succès de la sauvegarde
    // var isSaving by remember { mutableStateOf(false) }

    // Synchronisation de l'état asynchrone (user) avec l'état local
    LaunchedEffect(user) {
        user?.let { actualUser ->
            // Mettre à jour les champs si les données sont chargées et que les champs sont vides
            if (nameField.isEmpty()) {
                nameField = actualUser.name
            }
            if (emailField.isEmpty()) {
                emailField = actualUser.email
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(LightGrayBackground)
    ) {

        // --- 1. Top Bar ---
        Row (
            modifier = Modifier
                .fillMaxWidth()
                .height(70.dp)
                .background(colorResource(id = R.color.white)),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = DarkText)
            }
            Text(
                text = stringResource(R.string.edit_profile),
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                color = DarkText
            )
            IconButton(onClick = {}) {
                Icon(
                    Icons.Default.MoreVert,
                    contentDescription = "Menu",
                    tint = Color.Gray
                )
            }
        }

        // --- 2. Champs de Saisie (Zone Principale) ---
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 16.dp)
                .padding(top = 20.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Vérification de l'état de l'objet user avant l'utiliser
            if (user != null) {
                ProfileHeader(
                    name = user!!.name,
                    email = user!!.email,
                    imageUrl = user!!.avatar
                )
            } else {
                ProfileHeaderSkeleton()
            }

            OutlinedTextField(
                value = nameField,
                onValueChange = { nameField = it },
                label = { Text("Name") },
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = colorResource(id = R.color.sdGray).copy(alpha = 0.7f),
                    unfocusedContainerColor = colorResource(id = R.color.sdGray).copy(alpha = 0.7f),
                    focusedBorderColor = PrimaryGreen
                ),
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = emailField,
                onValueChange = { emailField = it },
                label = { Text("Email") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = colorResource(id = R.color.sdGray).copy(alpha = 0.7f),
                    unfocusedContainerColor = colorResource(id = R.color.sdGray).copy(alpha = 0.7f),
                    focusedBorderColor = PrimaryGreen
                ),
                modifier = Modifier.fillMaxWidth()
            )

        }

        // --- 3. Bouton de Confirmation ---
        Button(
            // Appel de la fonction de sauvegarde
            onClick = {
                navController.popBackStack()
            },
            colors = ButtonDefaults.buttonColors(containerColor = PrimaryGreen),
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .height(56.dp)
        ) {
            Text(
                text = "Confirm",
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}