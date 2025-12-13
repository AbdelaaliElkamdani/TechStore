package com.akn.techstore.project.models.repositories
import androidx.lifecycle.LiveData
import com.akn.techstore.project.database.TechStoreDao
import com.akn.techstore.project.models.dataModels.Favourite

class FavouriteRepository(private val techStoreDao : TechStoreDao) {

    val allFavourites : LiveData<List<Favourite>> = techStoreDao.getAllFavourites()

    suspend fun addToFavourite(favourite: Favourite) {
        techStoreDao.insertFavourite(favourite)
    }

    suspend fun removeFromFavourite(productId: Int) {
        techStoreDao.deleteFavouriteByProductId(productId)
    }

    suspend fun isFavourite(productId: Int) = techStoreDao.isFavourite(productId)
}