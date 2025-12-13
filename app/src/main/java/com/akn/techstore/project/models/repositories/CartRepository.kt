package com.akn.techstore.project.models.repositories

import androidx.lifecycle.LiveData
import com.akn.techstore.project.database.TechStoreDao
import com.akn.techstore.project.models.dataModels.Cart

class CartRepository(private val techStoreDao : TechStoreDao) {

    val allUnconfirmedCarts : LiveData<List<Cart>> = techStoreDao.getUnconfirmedCarts()

    val allConfirmedCarts : LiveData<List<Cart>> = techStoreDao.getConfirmedCarts()

    suspend fun addToCart(cart: Cart) {
        techStoreDao.insertCart(cart)
    }

    suspend fun removeFromCart(cartId: Int) {
        techStoreDao.deleteCartById(cartId)
    }

    suspend fun isInCart(productId: Int) = techStoreDao.isInCart(productId)

    suspend fun updateQuantity(quantity: Int, id: Int) {
        techStoreDao.updateQuantity(quantity, id)
    }

    fun confirmAllCarts() {
        techStoreDao.confirmAllCarts()
    }
}