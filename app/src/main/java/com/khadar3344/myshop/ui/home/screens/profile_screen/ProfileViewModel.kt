package com.khadar3344.myshop.ui.home.screens.profile_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.khadar3344.myshop.data.auth.repository.AuthRepository
import com.khadar3344.myshop.model.User
import com.khadar3344.myshop.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val repository: AuthRepository
) : ViewModel() {
    private val _userInfo = MutableStateFlow<Resource<User>?>(value = Resource.Idle)
    val userInfo: StateFlow<Resource<User>?> = _userInfo
    init {
        getUserInfoFromFirebase()
    }

    private fun getUserInfoFromFirebase() = viewModelScope.launch {
        _userInfo.value = Resource.Loading
        val result = repository.retrieveData()
        _userInfo.value = result
    }

    fun logout() {
        repository.logout()
        _userInfo.value = null
    }

    fun updateUserInfoFirebase(user: User) = viewModelScope.launch {
        repository.updateData(user = user)
    }
}