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
import com.akn.techstore.ui.viewModel.LoginViewModel

@Composable
fun LoginScreen(
    navigateTo: (AuthScreen) -> Unit,
    onAuthSuccess: () -> Unit,
    viewModel: LoginViewModel = viewModel()
) {

    val state by viewModel.state.collectAsState()

    LaunchedEffect(state.isLoginSuccessful) {
        if (state.isLoginSuccessful) {
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
            text = "Log In",
            color = colorResource(id = R.color.black),
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(top = 80.dp, bottom = 40.dp)
        )

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
        Spacer(Modifier.height(8.dp))

        // Lien "Forgot Password ?"
        Text(
            text = "Forgot password ?",
            color = colorResource(id = R.color.green),
            fontSize = 14.sp,
            modifier = Modifier
                .align(Alignment.Start)
                .clickable { /* Logique pour mot de passe oublié */ }
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

        // Bouton Login
        Button(
            onClick = viewModel::login,
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
                Text(text = "Login", fontSize = 18.sp, fontWeight = FontWeight.SemiBold)
            }
        }
        Spacer(Modifier.weight(1f)) // Pousse le texte en bas

        // Lien "Don't have an account ?"
        Row(
            modifier = Modifier.padding(bottom = 32.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "Don't have an account ? ", color = Color.Gray, fontSize = 14.sp)
            Text(
                text = "Sign up",
                color = colorResource(id = R.color.green),
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.clickable { navigateTo(AuthScreen.SIGNUP) }
            )
        }
    }
}