package com.antonov.tinkoff.fintex.data.repository.categories

import com.antonov.tinkoff.fintex.data.api.categories.CategoriesGifApiService

class CategoriesGIfRepository(private val categoriesGifApiService: CategoriesGifApiService) {
    suspend fun getCategoriesGif(categoriesName: String, page: Int) =
        categoriesGifApiService.getCategoriesGif(categoriesName, page)
}