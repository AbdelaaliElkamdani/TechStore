package com.akn.techstore.project.view

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
import com.akn.techstore.R
import com.akn.techstore.project.components.CartItemRow
import com.akn.techstore.project.components.PriceSummary
import com.akn.techstore.project.localData.products
import com.akn.techstore.project.model.data.Cart
import com.akn.techstore.project.model.data.Product
import com.akn.techstore.project.theme.*


data class CartWithProduct(
    val cart: Cart,
    val product: Product
)

@Composable
fun CartScreen() {
    val carts = listOf(
        Cart(id = 1, productId = 102),
        Cart(id = 2, productId = 301),
        Cart(id = 3, productId = 402),
    )

    val cartItems: List<CartWithProduct> = carts.mapNotNull { cart ->
        val product = products.find { it.id == cart.productId }
        product?.let { CartWithProduct(cart, product) }
    }

    val cartItemsState = remember { mutableStateListOf<CartWithProduct>().apply { addAll(cartItems) } }

    val updateQuantity: (CartWithProduct, Int) -> Unit = { item, newQuantity ->
        val index = cartItemsState.indexOfFirst { it.cart.id == item.cart.id }

        if (index != -1) {
            if (newQuantity <= 0) {
                cartItemsState.removeAt(index)
            } else {
                val updatedCart = item.cart.copy(quantity = newQuantity)
                cartItemsState[index] = item.copy(cart = updatedCart)
            }
        }
    }

    val subtotal = cartItemsState.sumOf { it.product.price * it.cart.quantity }

    val deliveryFee = 5.00
    val discountRate = 0.40
    val discount = if (subtotal > 0) subtotal * discountRate else 0.0

    val total = subtotal + deliveryFee - discount



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
                color = DarkText,
                modifier = Modifier
                    .padding(bottom = 16.dp)
                    .padding(top = 16.dp)
                    .padding(start = 16.dp)
            )
        }

        LazyColumn(
            modifier = Modifier.weight(1f),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(cartItemsState, key = { it.cart.id }) { item ->
                CartItemRow(
                    item = item,
                    onQuantityChange = { newQty -> updateQuantity(item, newQty) },
                    onRemove = { updateQuantity(item, 0) }
                )
            }

            item {
                PriceSummary(subtotal, deliveryFee, discount, total)
            }
        }

        Button(
            onClick = {  },
            colors = ButtonDefaults.buttonColors(containerColor = PrimaryGreen),
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .height(56.dp)
        ) {
            Text(
                text = "Checkout for ${"%.2f".format(total)} Dh",
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}