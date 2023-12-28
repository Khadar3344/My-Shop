package com.khadar3344.myshop.data.network.repository

import com.khadar3344.myshop.data.network.api.ApiService
import com.khadar3344.myshop.data.network.dto.Product
import com.khadar3344.myshop.util.Resource
import javax.inject.Inject

class NetworkRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : NetworkRepository {
    override suspend fun getProductsListFromApi(): Resource<List<Product>> {
        return try {
            val response = apiService.getProductsListFromApi()
            Resource.Success(response.products)
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Failure(e)
        }
    }

    override suspend fun getSingleProductByIdFromApi(productId: Int): Resource<Product> {
        return try {
            val response = apiService.getSingleProductByIdFromApi(productId = productId)
            Resource.Success(response)
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Failure(e)
        }
    }

    override suspend fun getProductsListBySearchFromApi(query: String): Resource<List<Product>> {
        return try {
            val response = apiService.getProductsListBySearchFromApi(query = query)
            Resource.Success(response.products)
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Failure(e)
        }
    }

    override suspend fun getAllCategoriesListFromApi(): Resource<List<String>> {
        return try {
            val response = apiService.getAllCategoriesListFromApi()
            Resource.Success(response)
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Failure(e)
        }
    }

    override suspend fun getProductsListByCategoryNameFromApi(categoryName: String): Resource<List<Product>> {
        return try {
            val response = apiService.getProductsListByCategoryNameFromApi(categoryName = categoryName)
            Resource.Success(response.products)
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Failure(e)
        }
    }
}