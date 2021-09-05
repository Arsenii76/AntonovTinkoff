package com.antonov.tinkoff.fintex.data.repository

import com.antonov.tinkoff.fintex.data.api.categories.GifsApiService

class GifsRepository(private val gifsApiService: GifsApiService) {
    suspend fun getGifs(categoriesName: String, page: Int) =
        gifsApiService.getGifs(categoriesName, page)
}