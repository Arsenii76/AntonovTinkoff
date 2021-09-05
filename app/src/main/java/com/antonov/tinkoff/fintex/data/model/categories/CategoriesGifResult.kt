package com.antonov.tinkoff.fintex.data.model.categories

import com.google.gson.annotations.SerializedName

data class CategoriesGifResult(
    val description: String,
    @SerializedName("gifURL") val url: String
)
