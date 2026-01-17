package com.oguzhan.shared.core.model

data class CoinDetailUiModel(
    val id: String,
    val name: String,
    val hashingAlgorithm: String,
    val description: String,
    val imageUrl: String? = null,
    val currentPrice: String,
    val priceChange24h: String,
    val isPositive: Boolean
)
