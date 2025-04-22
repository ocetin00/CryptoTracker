package com.oguzhan.shared.core.data.remote.model


import kotlinx.serialization.SerialName


data class CoinRemoteModel(
    @SerialName("id")
    val id: String,
    @SerialName("symbol")
    val symbol: String?,
    @SerialName("name")
    val name: String?
)
