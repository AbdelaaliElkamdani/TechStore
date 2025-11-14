package com.akn.techstore.project.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.akn.techstore.project.model.data.Product
import com.akn.techstore.project.model.repository.ProductRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


data class ProductDetailState(
    val product: Product? = null,
    val isLoading: Boolean = true,
    val error: String? = null,
    val isFavorite: Boolean = false
)

class ProductDetailViewModel(
    private val repository: ProductRepository = ProductRepository()
) : ViewModel() {
    private val _state = MutableStateFlow(ProductDetailState())
    val state: StateFlow<ProductDetailState> = _state

    fun loadProduct(productId: Int) {
        if (_state.value.product != null && _state.value.product!!.id == productId) {
            return
        }
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, error = null) }
            try {
                val product = repository.getProductById(productId)
                _state.update { it.copy(product = product, isLoading = false) }
            } catch (e: Exception) {
                _state.update { it.copy(error = "Erreur de chargement: ${e.message}", isLoading = false) }
            }
        }
    }
}