package com.khadar3344.myshop.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.khadar3344.myshop.data.local.models.FavoriteProduct
import com.khadar3344.myshop.data.local.models.UserCart

@Database(entities = [UserCart::class, FavoriteProduct::class], version = 1, exportSchema = false)
abstract class AppDatabase: RoomDatabase() {
    abstract fun appDao(): AppDao
}