package com.khadar3344.myshop.ui.home.screens.cart_screen

import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.khadar3344.myshop.data.local.models.UserCart
import com.khadar3344.myshop.data.local.repositories.LocalRepository
import com.khadar3344.myshop.util.Resource
import com.khadar3344.myshop.util.getUserIdFromSharedPref
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(
    private val localRepository: LocalRepository,
    private val sharedPreferences: SharedPreferences
) : ViewModel() {
    private val _userCart = MutableStateFlow<Resource<List<UserCart>>>(Resource.Idle)
    val userCart: StateFlow<Resource<List<UserCart>>> = _userCart

    private val _totalPrice: MutableStateFlow<Double> = MutableStateFlow(0.0)
    val totalPrice: StateFlow<Double> = _totalPrice

    private val _badgeCountState = MutableStateFlow(value = 0)
    val badgeCount: StateFlow<Int> = _badgeCountState.asStateFlow()

    init {
        getCartsByUserId()
        getBadgeCount()
    }

    private fun getCartsByUserId() = viewModelScope.launch {
        val result =
            localRepository.getUserCartByUserIdFromDb(getUserIdFromSharedPref(sharedPreferences))
        _userCart.value = result
    }

    fun updateTotalPrice(cartList: List<UserCart>) = viewModelScope.launch {
        _totalPrice.value = calculateTotalPrice(cartList)
    }

    fun updateBadgeCount(newCount: Int) = viewModelScope.launch {
        _badgeCountState.value = newCount
    }

    fun deleteUserCartItem(userCart: UserCart) = viewModelScope.launch {
        localRepository.deleteUserCartFromDb(userCart = userCart)
        getCartsByUserId()
    }

    fun updateUserCartItem(userCart: UserCart) = viewModelScope.launch {
        localRepository.updateUserCartFromDb(userCart = userCart)
        getCartsByUserId()
    }

    private fun getBadgeCount() = viewModelScope.launch {
        val result =
            localRepository.getBadgeCountFromDb(getUserIdFromSharedPref(sharedPreferences))
        _badgeCountState.value = result
    }

    private fun calculateTotalPrice(cartList: List<UserCart>): Double {
        var totalPrice = 0.0
        for (cart in cartList) {
            totalPrice += cart.price * cart.quantity
        }
        return totalPrice
    }
}