package com.antonov.tinkoff.fintex.data.api.random

import com.antonov.tinkoff.fintex.data.model.RandomGifResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface RandomGifApiService {

    @GET("random")
    suspend fun getGif(
        @Query("json") json: Boolean = true,
    ): RandomGifResponse
}