package com.antonov.tinkoff.fintex.ui.common.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.antonov.tinkoff.fintex.data.repository.categories.CategoriesGIfRepository

class CategoriesGifViewModelFactory(
    private val categoriesGIfRepository: CategoriesGIfRepository,
    private val categoriesName: String,
    private val page: Int,
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>) =
        CategoriesGifViewModel(categoriesGIfRepository, categoriesName, page) as T
}