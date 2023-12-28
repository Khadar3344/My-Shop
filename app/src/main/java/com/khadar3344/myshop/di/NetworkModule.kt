package com.khadar3344.myshop.di

import android.annotation.SuppressLint
import com.khadar3344.myshop.data.network.api.ApiService
import com.khadar3344.myshop.data.network.repository.NetworkRepository
import com.khadar3344.myshop.data.network.repository.NetworkRepositoryImpl
import com.khadar3344.myshop.util.Constants.Companion.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @SuppressLint("SuspiciousIndentation")
    @Provides
    @Singleton
    fun provideApi(): ApiService {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        return retrofit.create(ApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideNetworkRepository(apiService: ApiService): NetworkRepository {
        return NetworkRepositoryImpl(apiService = apiService)
    }
}