package com.akn.techstore.ui.model.repository
import com.akn.techstore.ui.model.data.Product

class FavouriteRepository {
    suspend fun updateProductStatus(productId: Int, newStatus: Boolean): Boolean {
        return true
    }

    suspend fun getFavouriteProducts(): List<Product> {
        return emptyList()
    }
}