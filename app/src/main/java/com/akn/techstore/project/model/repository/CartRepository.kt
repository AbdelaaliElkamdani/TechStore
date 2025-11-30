package com.akn.techstore.project.model.repository

import com.akn.techstore.project.model.data.Cart
import com.akn.techstore.project.model.data.Product
import kotlinx.coroutines.delay

class CartRepository {

    suspend fun getCartProducts(): List<Cart> {
        delay(2000)
        return listOf(Cart(1, 101), Cart(2, 102), Cart(3, 103))
    }
}