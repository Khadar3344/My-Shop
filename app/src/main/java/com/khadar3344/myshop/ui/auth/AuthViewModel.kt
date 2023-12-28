package com.khadar3344.myshop.ui.auth

import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.khadar3344.myshop.util.Resource
import com.khadar3344.myshop.data.auth.repository.AuthRepository
import com.google.firebase.auth.FirebaseUser
import com.khadar3344.myshop.model.User
import com.khadar3344.myshop.util.Constants.Companion.PREF_FIREBASE_USERID_KEY
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val repository: AuthRepository,
    private val sharedPreferences: SharedPreferences
) : ViewModel() {

    private val _loginFlow = MutableStateFlow<Resource<FirebaseUser>?>(Resource.Idle)
    val loginFlow: StateFlow<Resource<FirebaseUser>?> = _loginFlow

    private val _forgetPassFlow = MutableStateFlow<Resource<String>?>(Resource.Idle)
    val forgetPassFlow: StateFlow<Resource<String>?> = _forgetPassFlow

    val currentUser: FirebaseUser?
        get() = repository.currentUser


    init {
        if (repository.currentUser != null) {
            _loginFlow.value = Resource.Success(repository.currentUser!!)
        }
    }

    fun login(email: String, password: String) = viewModelScope.launch {
        _loginFlow.value = Resource.Loading
        val result = repository.login(email, password)
        _loginFlow.value = result
    }

    fun signup(user: User, password: String) = viewModelScope.launch {
        repository.signup(user, password)
    }


    fun forgetPass(email: String) = viewModelScope.launch {
        _forgetPassFlow.value = Resource.Loading
        val result = repository.forgotPassword(email)
        _forgetPassFlow.value = result
    }


    fun saveUserIdToSharedPref(id: String) {
        sharedPreferences.edit()
            .putString(PREF_FIREBASE_USERID_KEY, id)
            .apply()
    }
}


