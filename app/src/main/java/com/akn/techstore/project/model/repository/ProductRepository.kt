package com.akn.techstore.project.model.repository

import com.akn.techstore.project.localData.products
import com.akn.techstore.project.model.data.Product
import kotlinx.coroutines.delay

class ProductRepository {
    suspend fun getProducts(): List<Product> {
        delay(2000)
        return products
    }

    suspend fun getProductById(productId: Int): Product? {
        return products.find { it.id == productId }
    }
}