package com.akn.techstore.project.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.akn.techstore.project.models.dataModels.Setting
import com.akn.techstore.project.models.repositories.UserRepository
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


class NavigationViewModel(private val repo: UserRepository) : ViewModel() {

    private val auth: FirebaseAuth = Firebase.auth

    // État principal : Indique si l'utilisateur est connecté pour le NavHost
    private val _isLoggedIn = MutableStateFlow(false)
    val isLoggedIn: StateFlow<Boolean> = _isLoggedIn.asStateFlow()

    // Indique si la vérification de session initiale est en cours
    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    // Variable pour l'affichage de la Bottom Bar
    private val _showBottomBar = MutableStateFlow(false)
    val showBottomBar: StateFlow<Boolean> = _showBottomBar.asStateFlow()

    // L'état qui détermine si l'écran "Get Started" a été vu
    private val _isStarted = MutableStateFlow(false)
    val isStarted: StateFlow<Boolean> = _isStarted.asStateFlow()

    // L'observateur d'état de Firebase pour une réactivité instantanée
    private val authStateListener = FirebaseAuth.AuthStateListener { firebaseAuth ->
        val user = firebaseAuth.currentUser

        // Lance la coroutine pour le traitement asynchrone (vérification Room)
        viewModelScope.launch {

            // Lisez l'état isStarted. Si null (jamais inséré), c'est false.
            val startedStatus = repo.getIsStartedStatus() ?: false
            _isStarted.update { startedStatus }

            if (!startedStatus) {
                // Si l'onboarding n'a pas été vu, on s'arrête là.
                _isLoading.update { false }
                return@launch
            }

            if (user != null) {
                // Utilisateur connecté Firebase : on vérifie la persistance locale
                val localUser = repo.getLoggedInUser()

                if (localUser != null) {
                    _isLoggedIn.update { true } // L'utilisateur est connecté
                    _isStarted.update { true } // L'application est démarrée
                }
            } else {
                // Utilisateur déconnecté de Firebase.
                repo.clearUser() // Vider la base de données locale
                _isLoggedIn.update { false } // L'utilisateur est déconnecté
            }
            // L'opération est terminée, le NavHost peut décider de la destination
            _isLoading.update { false }
        }
    }

    init {
        // Démarre l'écoute des changements d'état d'authentification
        auth.addAuthStateListener(authStateListener)
    }

    override fun onCleared() {
        super.onCleared()
        // Retirer l'observateur pour éviter les fuites de mémoire (très important)
        auth.removeAuthStateListener(authStateListener)
    }

    fun enableBottomBar() { _showBottomBar.update { true } }
    fun disableBottomBar() { _showBottomBar.update { false } }

    // NOUVELLE FONCTION : Appelée par le bouton "Get Started"
    fun completeOnboarding() {
        viewModelScope.launch {
            // 1. Lire les paramètres actuels
            val currentSettings = repo.getSettings() ?: Setting(id = 1)

            // 2. Mettre à jour et insérer la nouvelle entité
            repo.insertSettings(currentSettings.copy(isStarted = true))

            // 3. Mettre à jour l'état du ViewModel
            _isStarted.update { true }

            // Déclencher la vérification de l'état d'authentification immédiatement après
            _isLoading.update { true }

            // Le listener va s'exécuter à la prochaine vérification, mais nous pouvons forcer la vérification
            val user = auth.currentUser
            if (user != null) {
                val localUser = repo.getLoggedInUser()
                _isLoggedIn.update { localUser != null }
            } else {
                _isLoggedIn.update { false }
            }
            _isLoading.update { false }
        }
    }

    fun logout() {
        viewModelScope.launch {
            _isLoading.update { true } // Afficher le chargement
            auth.signOut()
            // L'AuthStateListener gérera la suite (_isLoggedIn = false et _isLoading = false)
        }
    }
}
