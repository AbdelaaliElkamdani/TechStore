package com.akn.techstore.project.model.repository

import com.akn.techstore.project.api.service.ApiInstance

class ProductRepository {

    suspend fun getAllProducts() = ApiInstance.retrofitService.getProducts()

    suspend fun getProductsByCategory(category: String) =
        ApiInstance.retrofitService.getProductsByCategory(category)

    suspend fun getSearchProducts(searchQuery: String) =
        ApiInstance.retrofitService.getSearchProducts(searchQuery)

    suspend fun getProductById(productId: Int) =
        ApiInstance.retrofitService.getProductById(productId)
}