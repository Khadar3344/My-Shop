package com.khadar3344.myshop.data.network.repository

import com.khadar3344.myshop.data.network.dto.Product
import com.khadar3344.myshop.util.Resource

interface NetworkRepository {
    suspend fun getProductsListFromApi(): Resource<List<Product>>

    suspend fun getSingleProductByIdFromApi(productId: Int): Resource<Product>

    suspend fun getProductsListBySearchFromApi(query: String): Resource<List<Product>>

    suspend fun getAllCategoriesListFromApi(): Resource<List<String>>

    suspend fun getProductsListByCategoryNameFromApi(categoryName: String): Resource<List<Product>>
}