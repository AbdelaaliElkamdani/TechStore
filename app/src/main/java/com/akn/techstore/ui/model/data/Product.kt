package com.akn.techstore.ui.model.data

data class Product(
    val id: Int,
    val name: String,
    val price: Double,
    val rating: Double,
    val imageUrl: String,
    val isNew: Boolean = false
)