package com.akn.techstore.ui.model.data


data class Cart(
    val id: Int,
    val product: Product,
    val quantity: Int,
    val userId: Int,
    val createdAt: String
)
