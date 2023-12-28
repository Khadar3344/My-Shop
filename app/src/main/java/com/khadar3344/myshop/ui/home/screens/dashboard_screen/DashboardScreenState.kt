package com.khadar3344.myshop.ui.home.screens.dashboard_screen

import com.khadar3344.myshop.data.network.dto.Product

data class DashboardScreenState(
    val isLoading: Boolean = false,
    val productsList: List<Product> = emptyList(),
    val error: String? = null,
    val isSearchBarVisible: Boolean= false,
    val selectedProduct: Product? = null,
    val category: String = "smartphones",
    val searchQuery: String = ""
)
