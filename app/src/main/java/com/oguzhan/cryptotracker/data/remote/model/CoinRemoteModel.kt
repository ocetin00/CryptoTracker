package com.oguzhan.cryptotracker.data.remote.model


import com.google.gson.annotations.SerializedName


data class CoinRemoteModel(
    @SerializedName("id")
    val id: String,
    @SerializedName("symbol")
    val symbol: String?,
    @SerializedName("name")
    val name: String?
)
