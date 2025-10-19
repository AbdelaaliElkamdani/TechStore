package com.akn.techstore.ui.model.data

import kotlin.String

data class Product(
    val id: Int,
    val name: String,
    val price: Double,
    val rating: Double = 4.5,
    val description: String = "",
    val category: String = "",
    val color: String = "Blue",
    val imageUrl: String = "https://placehold.co/80x80/288D2A/FFFFFF?text=IMG",
    val isNew: Boolean = false
)