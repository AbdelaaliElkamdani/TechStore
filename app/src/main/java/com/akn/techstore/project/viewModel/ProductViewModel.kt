package com.akn.techstore.project.viewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.akn.techstore.project.models.repositories.ProductRepository
import androidx.lifecycle.viewModelScope
import com.akn.techstore.project.api.response.ApiResponse
import kotlinx.coroutines.launch


class ProductViewModel(
    // Injection de dépendance par défaut du Repository
    private val repository: ProductRepository = ProductRepository()
) : ViewModel() {

    // État mutable pour la liste de tous les produits ou des résultats de recherche
    val productState : MutableLiveData<ApiResponse> = MutableLiveData()

    private val _productIdState = MutableLiveData <ApiResponse?>()
    // État mutable interne pour un produit spécifique (par ID)
    val productIdState: LiveData<ApiResponse?> = _productIdState
    // État exposé à l'interface utilisateur pour un produit unique

    // Utilisation de 'mutableStateOf' pour l'affichage des erreurs dans Jetpack Compose
    var error by mutableStateOf<String?>(null)
        private set

    init {
        // Chargement initial des données dès l'instanciation du ViewModel
        loadProducts()
    }

    fun loadProducts() {
        viewModelScope.launch {
            error = null
            try {
                // Appel asynchrone pour obtenir tous les produits
                val response = repository.getAllProducts()
                // Mise à jour de l'état avec la réponse API
                productState.postValue(response.body())
            } catch (e: Exception) {
                // Gestion des exceptions (connexion, etc.)
                error = "Loading error: ${e.message}"
            }
        }
    }

    fun getSearchProducts(query: String) {
        viewModelScope.launch {
            try {
                // Appel asynchrone pour la recherche par requête
                val response = repository.getSearchProducts(query)
                if (response.isSuccessful) {
                    // Mise à jour de l'état avec les résultats de recherche
                    productState.postValue(response.body())
                }
            } catch (e: Exception) {
                error = "Loading error: ${e.message}"
            }
        }
    }

    fun getProductsByCategory(category: String) {
        viewModelScope.launch {
            try {
                // Appel asynchrone pour filtrer par catégorie
                val response = repository.getProductsByCategory(category)
                if (response.isSuccessful) {
                    // Mise à jour de l'état avec les produits filtrés
                    productState.postValue(response.body())
                }
            } catch (e: Exception) {
                error = "Loading error: ${e.message}"
            }
        }
    }

    fun getProductById(productId: Int) {
        viewModelScope.launch {
            try {
                error = null
                _productIdState.postValue(null)
                // Appel à la fonction suspendue du repository pour un produit unique
                val response = repository.getProductById(productId)
                // Mise à jour de l'état spécifique pour le produit par ID
                _productIdState.postValue(response.body())
            } catch (e: Exception) {
                error = "Loading error: ${e.message}"
            }
        }
    }
}

