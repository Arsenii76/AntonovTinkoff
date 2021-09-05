package com.antonov.tinkoff.fintex.data.model.random

import com.google.gson.annotations.SerializedName

data class GifResponse(
    val description: String,
    @SerializedName("gifURL") val url: String
)
