package com.khadar3344.myshop.data.auth.repository

import com.khadar3344.myshop.util.Resource
import com.google.firebase.auth.FirebaseUser
import com.khadar3344.myshop.model.User

interface AuthRepository {
    val currentUser: FirebaseUser?
    suspend fun login(email: String, password: String): Resource<FirebaseUser>
    suspend fun signup(user: User, password: String)
    suspend fun retrieveData(): Resource<User>
    suspend fun updateData(user: User)
    suspend fun forgotPassword(email: String): Resource<String>
    fun logout()
}