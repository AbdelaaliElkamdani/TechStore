package com.akn.techstore.project.model.data

import androidx.annotation.DrawableRes
import kotlin.String

data class Product(
    val id: Int,
    val name: String,
    val price: Double,
    val rating: Double = 4.5,
    val description: String = "",
    val category: String = "",
    val color: String = "Blue",
    @DrawableRes val imageResId: Int,
    val isNew: Boolean = false
)