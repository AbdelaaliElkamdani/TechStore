package com.akn.techstore.project.model.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "local_user")
data class User(
    @PrimaryKey
    val uid: String,
    val name: String,
    val email: String,
    val address : String = "",
    val avatar : String = "",
    val isLoggedIn: Boolean = true
)
