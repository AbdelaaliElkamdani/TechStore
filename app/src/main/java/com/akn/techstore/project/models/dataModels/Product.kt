package com.akn.techstore.project.models.dataModels

import com.google.gson.annotations.SerializedName
import kotlin.String

data class Product(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("price")
    val price: Double,
    @SerializedName("rating")
    val rating: Double = 4.5,
    @SerializedName("description")
    val description: String = "",
    @SerializedName("category")
    val category: String = "",
    @SerializedName("color")
    val color: String = "Blue",
    @SerializedName("imageUrl")
    val imageUrl: String = "",
    @SerializedName("isNew")
    val isNew: Boolean = false
)