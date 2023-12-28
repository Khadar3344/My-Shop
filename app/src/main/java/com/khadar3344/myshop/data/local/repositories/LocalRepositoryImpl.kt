package com.khadar3344.myshop.data.local.repositories

import com.khadar3344.myshop.data.local.AppDao
import com.khadar3344.myshop.data.local.models.FavoriteProduct
import com.khadar3344.myshop.data.local.models.UserCart
import com.khadar3344.myshop.util.Resource
import javax.inject.Inject

class LocalRepositoryImpl @Inject constructor(
    private val appDao: AppDao
) : LocalRepository {
    override suspend fun getUserCartByUserIdFromDb(userId: String): Resource<List<UserCart>> {
        return try {
            val result = appDao.getCartByUserId(userId = userId)
            Resource.Success(result)
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Failure(e)
        }

    }

    override suspend fun insertUserCartToDb(userCart: UserCart) {
        appDao.insertUserCart(userCart = userCart)
    }

    override suspend fun deleteUserCartFromDb(userCart: UserCart) {
        appDao.deleteUserCartItem(userCart = userCart)
    }

    override suspend fun updateUserCartFromDb(userCart: UserCart) {
        appDao.updateUserCartItem(userCart = userCart)
    }

    override suspend fun getFavoriteProductsFromDb(userId: String): Resource<List<FavoriteProduct>> {
        return try {
            val result = appDao.getFavoriteProducts(userId = userId)
            Resource.Success(result)
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Failure(e)
        }
    }

    override suspend fun insertFavoriteItemToDb(favoriteProduct: FavoriteProduct) {
        appDao.insertFavoriteItem(favoriteProduct = favoriteProduct)
    }

    override suspend fun deleteFavoriteItemFromDb(favoriteProduct: FavoriteProduct) {
        appDao.deleteFavoriteItem(favoriteProduct = favoriteProduct)
    }

    override suspend fun getBadgeCountFromDb(userId: String): Int {
        return appDao.getBadgeCount(userId = userId)
    }
}