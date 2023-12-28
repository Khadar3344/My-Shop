package com.khadar3344.myshop.di

import android.content.Context
import android.content.SharedPreferences
import com.khadar3344.myshop.util.Constants.Companion.PREF_FILE_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SharedPreferencesModule {
    @Singleton
    @Provides
    fun provideSharedPreferences(@ApplicationContext context: Context): SharedPreferences {
        return context.getSharedPreferences(PREF_FILE_NAME, Context.MODE_PRIVATE)
    }
}