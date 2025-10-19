package com.akn.techstore.ui.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.akn.techstore.DarkTextColor
import com.akn.techstore.LightGrayBackground
import com.akn.techstore.PrimaryGreen
import com.akn.techstore.R
import com.akn.techstore.ui.components.CartItemRow
import com.akn.techstore.ui.components.PriceSummary
import com.akn.techstore.ui.model.data.Cart
import com.akn.techstore.ui.model.data.Product
import com.akn.techstore.ui.model.repository.CartRepository

@Composable
fun CartScreen() {
    // État mutable du panier pour simuler les interactions (ajout/suppression)
    val cartItems = listOf(
        Cart(
            id = 1,
            Product(1, "Xbox Series X", 570.00),
            1,
            createdAt = "",
            userId = 1
        ),
        Cart(
            id = 2,
            Product(2, "Wireless Controller", 77.00, color = "Blue"),
            1,
            createdAt = "",
            userId = 1
        ),
        Cart(
            id = 3,
            Product(3, "Razer Kaira Pro", 153.00, color = "Green"),
            1,
            createdAt = "",
            userId = 1
        ),
    )

    val cartItemsState = remember { mutableStateListOf<Cart>().apply { addAll(cartItems) } }

    // Fonction pour mettre à jour la quantité d'un article
    val updateQuantity: (Cart, Int) -> Unit = { item, newQuantity ->
        val index = cartItemsState.indexOfFirst { it.product.id == item.product.id }
        if (index != -1) {
            if (newQuantity <= 0) {
                cartItemsState.removeAt(index)
            } else {
                cartItemsState[index] = item.copy(quantity = newQuantity)
            }
        }
    }

    // Calculs de prix
    val subtotal = cartItemsState.sumOf { it.product.price * it.quantity }
    val deliveryFee = 5.00
    val discountRate = 0.40 // 40%
    val discount = if (subtotal > 0) subtotal * discountRate else 0.0
    val total = subtotal + deliveryFee - discount

    // En-tête de l'écran (sans la barre de navigation complète)
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(LightGrayBackground)
    ) {
        Row (
            modifier = Modifier
                .fillMaxWidth()
                .background(colorResource(id = R.color.white))
        ) {

            Text(
                text = "My Cart",
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                color = DarkTextColor,
                modifier = Modifier
                    .padding(bottom = 16.dp)
                    .padding(top = 16.dp)
                    .padding(start = 16.dp)
            )
        }

        // Liste des articles du panier
        LazyColumn(
            modifier = Modifier.weight(1f),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(cartItemsState, key = { it.product.id }) { item ->
                CartItemRow(
                    item = item,
                    onQuantityChange = { newQty -> updateQuantity(item, newQty) },
                    onRemove = { updateQuantity(item, 0) }
                )
            }

            // Section Récapitulatif
            item {
                PriceSummary(subtotal, deliveryFee, discount, total)
            }
        }

        // Bouton de Checkout (Paiement)
        Button(
            onClick = { /* Action de paiement */ },
            colors = ButtonDefaults.buttonColors(containerColor = PrimaryGreen),
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .height(56.dp)
        ) {
            Text(
                text = "Checkout for $${"%.2f".format(total)}",
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}