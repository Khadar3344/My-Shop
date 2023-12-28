package com.khadar3344.myshop.ui.home.screens.dashboard_screen

import com.khadar3344.myshop.data.network.dto.Product

sealed class DashboardScreenEvent {
    data class OnProductClicked(var product: Product) : DashboardScreenEvent()
    data class OnCategoryChange(var category: String) : DashboardScreenEvent()
    data class OnSearchQueryChanged(var searchQuery: String) : DashboardScreenEvent()
    object OnSearchIconClicked: DashboardScreenEvent()
    object OnCloseIconClicked: DashboardScreenEvent()
}
