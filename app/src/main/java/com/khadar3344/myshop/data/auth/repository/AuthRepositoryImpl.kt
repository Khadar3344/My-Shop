package com.khadar3344.myshop.data.auth.repository

import com.khadar3344.myshop.util.Resource
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.userProfileChangeRequest
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.khadar3344.myshop.model.User
import com.khadar3344.myshop.util.Constants
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth
) : AuthRepository {
    override val currentUser: FirebaseUser?
        get() = firebaseAuth.currentUser

    private val collection = Firebase.firestore
        .collection(Constants.USERS_COLLECTION)

    override suspend fun login(email: String, password: String): Resource<FirebaseUser> {
        return try {
            val result = firebaseAuth.signInWithEmailAndPassword(email, password).await()
            Resource.Success(result.user!!)
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Failure(e)
        }
    }

    override suspend fun signup(
        user: User,
        password: String
    ) {
        firebaseAuth.createUserWithEmailAndPassword(user.email, password)
            .addOnCompleteListener {
                if (it.isSuccessful)
                    collection.document(currentUser!!.uid)
                        .set(user)
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                Resource.Success(user)
                            } else
                                Resource.Failure(task.exception.toString())

                        }
                else
                    Resource.Failure(it.exception.toString())
            }
    }

    override suspend fun retrieveData(): Resource<User> {
        val result = collection.document(currentUser!!.uid).get().await()
        return try {
            val user = User(
                name = result.getString("name") ?: "",
                phone = result.getString("phone") ?: "",
                address = result.getString("address") ?: "",
                email = result.getString("email") ?: ""
            )
            Resource.Success(user)
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Failure(e)
        }
    }

    override suspend fun updateData(user: User) {
        val userMap = hashMapOf(
            "name" to user.name,
            "phone" to user.phone,
            "address" to user.address,
            "email" to user.email
        )
        val profileUpdate = userProfileChangeRequest {
            displayName = userMap["name"]
        }
        currentUser?.updateProfile(profileUpdate)
        collection.document(firebaseAuth.uid!!).set(userMap).await()
    }

    override suspend fun forgotPassword(email: String): Resource<String> {
        return try {
            val result = firebaseAuth.sendPasswordResetEmail(email).await().toString()
            Resource.Success(result)
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Failure(e)
        }
    }

    override fun logout() {
        firebaseAuth.signOut()
    }
}