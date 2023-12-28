package com.khadar3344.myshop.di

import com.khadar3344.myshop.data.auth.repository.AuthRepository
import com.khadar3344.myshop.data.auth.repository.AuthRepositoryImpl
import com.google.firebase.auth.FirebaseAuth
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)

class FirebaseModule {

    @Provides
    fun provideFirebaseAuth(): FirebaseAuth = FirebaseAuth.getInstance()

    @Provides
    fun provideAuthRepository(impl: AuthRepositoryImpl): AuthRepository = impl
}