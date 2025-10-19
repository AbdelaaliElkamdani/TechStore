package com.akn.techstore.ui.model.repository

import com.akn.techstore.ui.model.data.Cart
import com.akn.techstore.ui.model.data.Product
import kotlinx.coroutines.delay

class CartRepository {

    suspend fun getCartProducts(): List<Cart> {
        delay(2000)
        return listOf(
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
    }
}