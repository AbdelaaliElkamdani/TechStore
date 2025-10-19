package com.akn.techstore.ui.view

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.akn.techstore.R
import com.akn.techstore.ui.navigation.AuthScreen
import com.akn.techstore.ui.viewModel.SignUpViewModel
import com.google.android.material.progressindicator.CircularProgressIndicator

@Composable
fun SignUpScreen(
    navigateTo: (AuthScreen) -> Unit,
    onAuthSuccess: () -> Unit,
    viewModel: SignUpViewModel = viewModel()
) {
    val state by viewModel.state.collectAsState()
    // 2. Gestion de la Navigation (Effet Secondaire)
    LaunchedEffect(state.isSingUpSuccessful) {
        if (state.isSingUpSuccessful) {
            onAuthSuccess()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(horizontal = 32.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Titre de l'écran
        Text(
            text = "Sign Up",
            color = colorResource(id = R.color.black),
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(top = 80.dp, bottom = 40.dp)
        )

        // Champ Nom
        OutlinedTextField(
            value = state.name,
            onValueChange = viewModel::onNameChange,
            label = { Text("Name") },
            shape = RoundedCornerShape(12.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = colorResource(id = R.color.sdGray).copy(alpha = 0.7f),
                unfocusedContainerColor = colorResource(id = R.color.sdGray).copy(alpha = 0.7f)
            ),
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.height(20.dp))

        // Champ Email
        OutlinedTextField(
            value = state.email,
            onValueChange = viewModel::onEmailChange,
            label = { Text("Email") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            shape = RoundedCornerShape(12.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = colorResource(id = R.color.sdGray).copy(alpha = 0.7f),
                unfocusedContainerColor = colorResource(id = R.color.sdGray).copy(alpha = 0.7f)
            ),
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.height(20.dp))

        // Champ Mot de passe
        OutlinedTextField(
            value = state.password,
            onValueChange = viewModel::onPasswordChange,
            label = { Text("Password") },
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            shape = RoundedCornerShape(12.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = colorResource(id = R.color.sdGray).copy(alpha = 0.7f),
                unfocusedContainerColor = colorResource(id = R.color.sdGray).copy(alpha = 0.7f)
            ),
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.height(20.dp))

        // Champ Confirmer mot de passe
        OutlinedTextField(
            value = state.confirmPassword,
            onValueChange = viewModel::onConfirmPasswordChange,
            label = { Text("Confirm password") },
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            shape = RoundedCornerShape(12.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = colorResource(id = R.color.sdGray).copy(alpha = 0.7f),
                unfocusedContainerColor = colorResource(id = R.color.sdGray).copy(alpha = 0.7f)
            ),
            modifier = Modifier.fillMaxWidth()
        )
        // Affichage de l'erreur
        state.error?.let { error ->
            Spacer(Modifier.height(8.dp))
            Text(
                text = error,
                color = Color.Red,
                fontSize = 14.sp,
                modifier = Modifier.align(Alignment.Start)
            )
        }

        Spacer(Modifier.height(32.dp))

        // Bouton Sign up
        Button(
            onClick = viewModel::signUp,
            enabled = !state.isLoading,
            colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.green)),
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
        ) {
            if (state.isLoading) {
                CircularProgressIndicator(modifier = Modifier.size(18.dp))
            } else {
                Text(text = "Sign up", fontSize = 18.sp, fontWeight = FontWeight.SemiBold)
            }
        }
        Spacer(Modifier.weight(1f)) // Pousse le texte en bas

        // Lien "Already have an account ? Login"
        Row(
            modifier = Modifier.padding(bottom = 32.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "Already have an account ? ", color = Color.Gray, fontSize = 14.sp)
            Text(
                text = "Login",
                color = colorResource(id = R.color.green),
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.clickable { navigateTo(AuthScreen.LOGIN) }
            )
        }
    }
}