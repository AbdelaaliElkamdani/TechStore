package com.akn.techstore.project.view

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.akn.techstore.R
import com.akn.techstore.project.components.CartItemRow
import com.akn.techstore.project.components.PriceSummary
import com.akn.techstore.project.model.data.Cart
import com.akn.techstore.project.theme.*
import com.akn.techstore.project.viewModel.CartViewModel
import com.akn.techstore.project.viewModelProvider.CartViewModelProviderFactory

@Composable
fun CartScreen(
    viewModel: CartViewModel = viewModel(factory = CartViewModelProviderFactory(LocalContext.current))
) {

    val cartItems by viewModel.allCarts.observeAsState(emptyList())

    // Observation des StateFlows de prix
    val subtotalHT by viewModel.subtotalHT.collectAsState()
    val vat by viewModel.vat.collectAsState()
    val totalTTC by viewModel.totalTTC.collectAsState()

    // Constante pour la livraison
    val deliveryFee = viewModel.DELIVERY_FEE

    // Recuperation du context
    val context = LocalContext.current

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
                text = stringResource(R.string.cart_title),
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                color = DarkText,
                modifier = Modifier
                    .padding(bottom = 16.dp)
                    .padding(top = 16.dp)
                    .padding(start = 16.dp)
            )
        }

        if (cartItems.isNotEmpty()) {

            // Fonction pour mettre à jour la quantité dans la base de données
            val updateQuantity: (Cart, Int) -> Unit = { item, newQuantity ->
                if (newQuantity <= 0) {
                    viewModel.removeFromCart(item.id)
                } else {
                    viewModel.updateQuantity(newQuantity, item.id)
                }
            }

            LazyColumn(
                modifier = Modifier.weight(1f),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Utilisation directe de cartItems
                items(cartItems, key = { it.id }) { item ->
                    CartItemRow(
                        item = item,
                        onQuantityChange = { newQty -> updateQuantity(item, newQty) },
                        onRemove = { updateQuantity(item, 0) }
                    )
                }

                item {
                    // Passage des variables d'état observées
                    PriceSummary(
                        subtotalHT = subtotalHT,
                        deliveryFee = deliveryFee,
                        VAT = vat,
                        totalTTC = totalTTC
                    )
                }
            }

            Button(
                onClick = {
                    viewModel.checkout()
                    Toast.makeText(context, "Checkout successful!", Toast.LENGTH_SHORT).show()
                },
                colors = ButtonDefaults.buttonColors(containerColor = PrimaryGreen),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .height(56.dp)
            ) {
                Text(
                    text = "Checkout for ${"%.2f".format(totalTTC)} Dh",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }
        } else {
            // ... (Panier vide) ...
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "No products in the cart found",
                    fontSize = 18.sp,
                    color = Color.Gray,
                    modifier = Modifier.padding(16.dp)
                )
            }
        }
    }
}