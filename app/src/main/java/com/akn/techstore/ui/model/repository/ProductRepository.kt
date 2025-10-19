package com.akn.techstore.ui.model.repository

import com.akn.techstore.ui.model.data.Product
import kotlinx.coroutines.delay

class ProductRepository {
    suspend fun getProducts(): List<Product> {

        // Replace this with your actual data retrieval logic

        delay(2000)

        return listOf(
            Product(
                1,
                "Smartphone Pro",
                899.99,
                4.5,
                "https://placehold.co/100x100/1E1E1E/FFFFFF?text=P1",
                isNew = true
            ),
            Product(
                2,
                "Headphones ANC",
                249.99,
                4.2,
                "https://placehold.co/100x100/4CAF50/FFFFFF?text=P2"
            ),
            Product(
                3,
                "Laptop Ultrabook",
                1299.00,
                4.8,
                "https://placehold.co/100x100/1E1E1E/FFFFFF?text=P3"
            ),
            Product(
                4,
                "Smartwatch X",
                349.99,
                4.6,
                "https://placehold.co/100x100/4CAF50/FFFFFF?text=P4",
                isNew = true
            ),
        )
    }
}