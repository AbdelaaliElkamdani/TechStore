package com.akn.techstore.project.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.akn.techstore.project.model.repository.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class LoginState(
    val email: String = "test@example.com",
    val password: String = "password123",
    val isLoading: Boolean = false,
    val error: String? = null,
    val isLoginSuccessful: Boolean = false
)

data class SignUpState(
    val name: String = "",
    val email: String = "",
    val password: String = "",
    val confirmPassword: String = "",
    val isLoading: Boolean = false,
    val error: String? = null,
    val isSingUpSuccessful: Boolean = false
)

class LoginViewModel(
    private val repository: UserRepository = UserRepository()
) : ViewModel() {

    private val _state = MutableStateFlow(LoginState())
    val state: StateFlow<LoginState> = _state


    // Fonction pour mettre à jour les champs de texte (appelée par l'UI)
    fun onEmailChange(newEmail: String) {
        _state.update { it.copy(email = newEmail, error = null) }
    }

    fun onPasswordChange(newPassword: String) {
        _state.update { it.copy(password = newPassword, error = null) }
    }

    fun login() {

        val currentState = _state.value

        if (currentState.email.isBlank() || currentState.password.isBlank()) {
            _state.update {
                it.copy(error = "Veuillez remplir tous les champs.")
            }
            return
        }

        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, error = null) }
            try {
                val result = repository.login(currentState.email, currentState.password)
                if (result) {
                    _state.update { it.copy(isLoading = false, isLoginSuccessful = true) }
                } else {
                    _state.update { it.copy(isLoading = false, error = "Identifiants incorrects.") }
                }
            } catch (e: Exception) {
                // Appel API échoué
                _state.update { it.copy(isLoading = false, error = "Erreur de connexion.") }
            }
        }
    }
}

class SignUpViewModel(
    private val repository: UserRepository = UserRepository()
) : ViewModel() {

    private val _state = MutableStateFlow(SignUpState())
    val state: StateFlow<SignUpState> = _state

    fun onNameChange(newName: String) {
        _state.update { it.copy(name = newName, error = null) }
    }
    fun onEmailChange(newEmail: String) {
        _state.update { it.copy(email = newEmail, error = null) }
    }
    fun onPasswordChange(newPassword: String) {
        _state.update { it.copy(password = newPassword, error = null) }
    }
    fun onConfirmPasswordChange(newConfirmPassword: String) {
        _state.update { it.copy(confirmPassword = newConfirmPassword, error = null) }
    }

    fun signUp() {
        val currentState = _state.value
        if (
            currentState.name.isBlank() ||
            currentState.email.isBlank() ||
            currentState.password.isBlank() ||
            currentState.confirmPassword.isBlank()
            ) {
            _state.update {
                it.copy(error = "Veuillez remplir tous les champs.")
            }
            return
        }
        // Validation n°1 : Vérification si les mots de passe correspondent
        if (currentState.password != currentState.confirmPassword) {
            _state.update {
                it.copy(error = "Les mots de passe ne correspondent pas.")
            }
            return
        }

        // 2. Lancement de la Coroutine pour l'appel API
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, error = null) }
            try {
                val result = repository.register(currentState.name, currentState.email, currentState.password)
                if (result) {
                    _state.update { it.copy(isLoading = false, isSingUpSuccessful = true) }
                } else {
                    _state.update { it.copy(isLoading = false, error = "Erreur lors de l'inscription.") }
                }
            } catch (e: Exception) {
                _state.update { it.copy(isLoading = false, error = "Erreur de connexion.") }
            }
        }
    }
}