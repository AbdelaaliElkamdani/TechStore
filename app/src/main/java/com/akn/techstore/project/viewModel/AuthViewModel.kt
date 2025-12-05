package com.akn.techstore.project.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.akn.techstore.project.model.repository.UserRepository
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
    val isSignUpSuccessful: Boolean = false
)

class LoginViewModel(
    private val repository: UserRepository = UserRepository()
) : ViewModel() {

    private val _state: MutableLiveData<LoginState> = MutableLiveData(LoginState())
    val state: LiveData<LoginState> = _state

    fun onEmailChange(newEmail: String) {
        _state.value = _state.value?.copy(email = newEmail, error = null)
    }

    fun onPasswordChange(newPassword: String) {
        _state.value = _state.value?.copy(password = newPassword, error = null)
    }

    fun login() {

        val currentState = _state.value ?: return

        if (currentState.email.isBlank() || currentState.password.isBlank()) {
            _state.value = currentState.copy(error = "Veuillez remplir tous les champs.")
            return
        }

        viewModelScope.launch {
            _state.value = currentState.copy(isLoading = true, error = null)
            try {
                val result = repository.login(currentState.email, currentState.password)
                if (result) {
                    _state.value = currentState.copy(isLoading = false, isLoginSuccessful = true)
                } else {
                    _state.value = currentState.copy(isLoading = false, error = "Identifiants incorrects.")
                }
            } catch (e: Exception) {
                _state.value = currentState.copy(isLoading = false, error = "Erreur de connexion.")
            }
        }
    }

    fun resetLoginSuccess() {
        _state.value = _state.value?.copy(isLoginSuccessful = false)
    }
}

class SignUpViewModel(
    private val repository: UserRepository = UserRepository()
) : ViewModel() {

    private val _state = MutableLiveData(SignUpState())
    val state: LiveData<SignUpState> = _state

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

        if (
            currentState.name.isBlank() ||
            currentState.email.isBlank() ||
            currentState.password.isBlank() ||
            currentState.confirmPassword.isBlank()
        ) {
            _state.value = currentState.copy(error = "Veuillez remplir tous les champs.")
            return
        }
        if (currentState.password != currentState.confirmPassword) {
            _state.value = currentState.copy(error = "Les mots de passe ne correspondent pas.")
            return
        }

        viewModelScope.launch {
            _state.value = currentState.copy(isLoading = true, error = null)
            try {
                val result = repository.register(currentState.name, currentState.email, currentState.password)
                if (result) {
                    _state.value = currentState.copy(isLoading = false, isSignUpSuccessful = true)
                } else {
                    _state.value = currentState.copy(isLoading = false, error = "Erreur lors de l'inscription.")
                }
            } catch (e: Exception) {
                _state.value = currentState.copy(isLoading = false, error = "Erreur de connexion.")
            }
        }
    }
    fun resetSignUpSuccess() {
        _state.value = _state.value?.copy(isSignUpSuccessful = false)
    }
}