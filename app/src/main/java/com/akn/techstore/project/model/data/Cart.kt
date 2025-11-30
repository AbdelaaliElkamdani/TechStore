package com.akn.techstore.project.model.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cart_table")
data class Cart(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Int = 0,
    @ColumnInfo(name = "productId")
    val productId: Int,
    @ColumnInfo(name = "quantity")
    val quantity: Int = 1,
)
