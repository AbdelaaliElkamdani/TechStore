package com.akn.techstore.project.models.dataModels

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cart_table")
data class Cart(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Int = 0,
    val product: Product,
    val quantity: Int = 1,
    val isConfirmed: Boolean = false,
    val inCart: Boolean = true
)
