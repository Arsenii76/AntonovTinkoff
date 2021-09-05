package com.antonov.tinkoff.fintex.data.repository.random

import com.antonov.tinkoff.fintex.data.api.random.RandomGifApiService

class RandomGifRepository(private val randomGifApiService: RandomGifApiService) {

    suspend fun getRandomGif() = randomGifApiService.getGif()

}