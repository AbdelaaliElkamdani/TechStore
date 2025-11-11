package com.akn.techstore.project.viewModel

import androidx.lifecycle.ViewModel
import com.akn.techstore.project.model.data.Product
import com.akn.techstore.project.model.repository.ProductRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

data class ProductListState(
    val products: List<Product> = emptyList(),
    val favoriteIds: Set<String> = emptySet(),
    val isLoading: Boolean = true,
    val error: String? = null
)

class ProductListViewModel(
    private val repository: ProductRepository = ProductRepository()
) : ViewModel() {

    private val _state = MutableStateFlow(ProductListState())
    val state: StateFlow<ProductListState> = _state

    init { loadProducts() }

    private fun loadProducts() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, error = null) }
            try {
                val products = repository.getProducts()
                _state.update { it.copy(products = products, isLoading = false) }
            } catch (e: Exception) {
                _state.update { it.copy(error = "Erreur de chargement: ${e.message}", isLoading = false) }
            }
        }
    }
}

