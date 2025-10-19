package com.akn.techstore.ui.model.repository

import com.akn.techstore.ui.model.data.User
import kotlinx.coroutines.delay

class UserRepository {

    suspend fun getUser(): User {
        return User(
            id = 1,
            name = "Emma Johnson",
            email = "emma.johnson@email.com",
            avatar = "https://placehold.co/100x100/CCCCCC/FFFFFF?text=EJ",
            password = "",
            address = ""
        )
    }

    suspend fun addUser(user: User): Boolean {
        return true
    }

    suspend fun updateUserInformations(user: User): Boolean {
        return true
    }

    suspend fun checkUserPassword(user: User): Boolean {
        return true
    }


    suspend fun updateUserPassword(user: User): Boolean {
        return true
    }

    suspend fun updateUserAddress(user: User): Boolean {
        return true
    }

    suspend fun updateUserAvatar(user: User): Boolean {
        return true
    }

    suspend fun login(email: String,password: String): Boolean {
        var userEmail = "test@example.com"
        var userPassword = "password123"
        delay(2000)
        if (email != userEmail || password != userPassword) {
            return false
        }
        return true
    }

    suspend fun register(
        name: String,
        email: String,
        password: String
    ): Boolean {
        delay(2000)
        return true
    }

    suspend fun logout(): Boolean {
        return true
    }
}