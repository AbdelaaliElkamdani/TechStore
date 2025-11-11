package com.akn.techstore.project.model.repository
import com.akn.techstore.project.model.data.Product

class FavouriteRepository {
    suspend fun updateProductStatus(productId: Int, newStatus: Boolean): Boolean {
        return true
    }

    suspend fun getFavouriteProducts(): List<Product> {
        return emptyList()
    }
}