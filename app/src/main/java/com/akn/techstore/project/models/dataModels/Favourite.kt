package com.akn.techstore.project.models.dataModels

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favourite_table")
data class Favourite(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val isFavourite: Boolean = true,
    val product: Product
)
