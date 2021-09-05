package com.antonov.tinkoff.fintex.data.api.categories

import com.antonov.tinkoff.fintex.data.model.categories.CategoriesGifResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface CategoriesGifApiService {

    @GET("{categories}/{page}")
    suspend fun getCategoriesGif(
        @Path(value = "categories") categories: String = "latest",
        @Path(value = "page") page: Int = 0,
        @Query("json") json: Boolean = true,
    ): CategoriesGifResponse
}