package com.akn.techstore.project.viewModel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.akn.techstore.project.models.dataModels.Cart
import com.akn.techstore.project.models.repositories.CartRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class CartViewModel(private val repository: CartRepository) : ViewModel() {

    // Constantes pour les calculs (Logique Métier - Maroc)
    val DELIVERY_FEE = 50.00
    val VAT_RATE = 0.20 // 20% de TVA

    // Liste des articles du panier (observée)
    val allCarts: LiveData<List<Cart>> = repository.allUnconfirmedCarts

    // Liste des articles du panier (observée)
    val allConfirmedCarts: LiveData<List<Cart>> = repository.allConfirmedCarts

    // --- Variables d'état pour les calculs de prix ---
    private val _subtotalHT = MutableStateFlow(0.0)
    val subtotalHT: StateFlow<Double> = _subtotalHT.asStateFlow()

    private val _vat = MutableStateFlow(0.0)
    val vat: StateFlow<Double> = _vat.asStateFlow()

    private val _totalTTC = MutableStateFlow(0.0)
    val totalTTC: StateFlow<Double> = _totalTTC.asStateFlow()
    // --------------------------------------------------

    var error = mutableStateOf<String?>(null)
        private set

    var loading = mutableStateOf(false)
        private set

    private val _isInCart = MutableStateFlow(false)
    val isInCart: StateFlow<Boolean> = _isInCart.asStateFlow()

    init {
        // Observez LiveData et mettez à jour les Flows de prix
        // C'est la clé pour déplacer la logique de prix vers le ViewModel
        repository.allUnconfirmedCarts.observeForever { cartList ->
            calculatePrices(cartList)
        }
    }

    /**
     * Calcule le total TTC et dérive les totaux HT et la TVA,
     * en partant du principe que product.price est déjà TTC.
     */
    private fun calculatePrices(cartList: List<Cart>) {

        // 1. Calculer le Sous-total BRUT (TTC) des produits
        val subtotalTTC_Products = cartList.sumOf { it.product.price * it.quantity }

        // 2. Déduire le Prix HT et la TVA pour chaque article
        var totalVAT = 0.0
        var subtotalHT_Calculated = 0.0

        if (subtotalTTC_Products > 0) {
            // Calculer le Sous-total HT Global des produits
            subtotalHT_Calculated = subtotalTTC_Products / (1 + VAT_RATE)

            // Calculer la TVA Totale des produits
            totalVAT = subtotalTTC_Products - subtotalHT_Calculated
        }

        // 3. Calculer le Total Final (Total TTC)
        // Note : Le total TTC inclut les frais de livraison.
        val finalTotalTTC = subtotalTTC_Products + DELIVERY_FEE

        // 4. Mise à jour des StateFlows
        // Le sous-total affiché sera désormais le Sous-total HT
        _subtotalHT.value = subtotalHT_Calculated
        _vat.value = totalVAT
        _totalTTC.value = finalTotalTTC
    }

    fun addToCart(cart: Cart) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addToCart(cart)
            checkInCart(cart.product.id)
            // L'appel au repository déclenche la mise à jour de allCarts,
            // qui à son tour déclenche calculatePrices via observeForever.
        }
    }

    fun removeFromCart(cartId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.removeFromCart(cartId)
            // Déclenche la mise à jour des prix
        }
    }

    fun updateQuantity(quantity: Int, id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateQuantity(quantity, id)
            // Déclenche la mise à jour des prix
        }
    }

    fun checkInCart(productId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = repository.isInCart(productId) ?: false
            _isInCart.value = result // Utilisez value = result directement
        }
    }

    fun checkout() {
        // Cela devrait être exécuté dans un thread secondaire car ce n'est pas une requête LiveData
        // Par exemple, en utilisant Coroutines:
        viewModelScope.launch(Dispatchers.IO) {
            repository.confirmAllCarts()
        }
    }
}
