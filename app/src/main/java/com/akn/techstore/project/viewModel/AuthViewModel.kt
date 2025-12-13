package com.akn.techstore.project.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.akn.techstore.project.model.repository.UserRepository
import kotlinx.coroutines.launch


data class LoginState(
    val email: String = "",
    val password: String = "",
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
    val isSignUpSuccessful: Boolean = false
)

class LoginViewModel(
    private val repository: UserRepository
) : ViewModel() {

    private val _state: MutableLiveData<LoginState> = MutableLiveData(LoginState())
    // État mutable interne
    val state: LiveData<LoginState> = _state
    // État exposé à l'interface utilisateur

    fun onEmailChange(newEmail: String) {
        // Mise à jour de l'état (immutable) et effacement de l'erreur précédente
        _state.value = _state.value?.copy(email = newEmail, error = null)
    }

    fun onPasswordChange(newPassword: String) {
        // Mise à jour du mot de passe
        _state.value = _state.value?.copy(password = newPassword, error = null)
    }

    fun login() {
        val currentState = _state.value ?: return
        if (currentState.email.isBlank() || currentState.password.isBlank()) {
            // Validation simple des champs vides (Message d'erreur traduit)
            _state.value = currentState.copy(error = "Please fill in all fields.")
            return
        }

        viewModelScope.launch {
            // Démarrer la coroutine pour l'opération asynchrone
            _state.value = currentState.copy(isLoading = true, error = null)
            try {
                repository.login(currentState.email, currentState.password)
                // Succès : mise à jour de l'état pour la navigation
                _state.value = currentState.copy(isLoading = false, isLoginSuccessful = true)
            } catch (e: Exception) {
                // Gestion des erreurs et arrêt du chargement
                val errorMessage = repository.getErrorMessage(e)
                _state.value = currentState.copy(isLoading = false, error = errorMessage)
            }
        }
    }
}

class SignUpViewModel(
    private val repository: UserRepository
) : ViewModel() {

    private val _state = MutableLiveData(SignUpState())
    // État mutable interne
    val state: LiveData<SignUpState> = _state
    // État exposé à l'interface utilisateur

    // Mise à jour de l'état pour les champs d'entrée
    fun onNameChange(newName: String) {
        _state.value = _state.value?.copy(name = newName, error = null)
    }
    fun onEmailChange(newEmail: String) {
        _state.value = _state.value?.copy(email = newEmail, error = null)
    }
    fun onPasswordChange(newPassword: String) {
        _state.value = _state.value?.copy(password = newPassword, error = null)
    }
    fun onConfirmPasswordChange(newConfirmPassword: String) {
        _state.value = _state.value?.copy(confirmPassword = newConfirmPassword, error = null)
    }

    fun signUp() {
        val currentState = _state.value ?: return

        // Validation : vérifier tous les champs sont remplis (Message d'erreur traduit)
        if (
            currentState.name.isBlank() ||
            currentState.email.isBlank() ||
            currentState.password.isBlank() ||
            currentState.confirmPassword.isBlank()
        ) {
            _state.value = currentState.copy(error = "Please fill in all fields.")
            return
        }
        // Validation : les mots de passe doivent correspondre (Message d'erreur traduit)
        if (currentState.password != currentState.confirmPassword) {
            _state.value = currentState.copy(error = "The passwords do not match.")
            return
        }
        viewModelScope.launch {
            // Démarrage du chargement pour l'appel API
            _state.value = currentState.copy(isLoading = true, error = null)
            try {
                repository.register(currentState.name, currentState.email, currentState.password)
                // Succès de l'inscription
                _state.value = currentState.copy(isLoading = false, isSignUpSuccessful = true)
            } catch (e: Exception) {
                // Gestion des erreurs
                val errorMessage = repository.getErrorMessage(e)
                _state.value = currentState.copy(isLoading = false, error = errorMessage)
            }
        }
    }
}