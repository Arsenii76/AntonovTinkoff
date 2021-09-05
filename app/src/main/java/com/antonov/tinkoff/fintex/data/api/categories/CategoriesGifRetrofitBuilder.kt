package com.antonov.tinkoff.fintex.data.api.categories

import com.antonov.tinkoff.fintex.data.api.random.RandomGifApiService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object CategoriesGifRetrofitBuilder {

    private const val BASE_URL = "https://developerslife.ru/"

    private val interceptor = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

    private val client = OkHttpClient.Builder()
        .addInterceptor(interceptor)

    private fun getRetrofit() : Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client.build())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val categoriesGifApiService = getRetrofit().create(CategoriesGifApiService::class.java)
}