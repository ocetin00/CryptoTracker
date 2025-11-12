package com.oguzhan.shared.core.mapper

import com.oguzhan.cryptotracker.domain.model.CoinDetailUiModel
import com.oguzhan.shared.core.data.remote.model.CoinDetailRemoteModel

object CoinDetailMapper {

    fun CoinDetailRemoteModel.toUiModel() = CoinDetailUiModel(
        id = id,
        name = name ?: "N/A",
        hashingAlgorithm = hashingAlgorithm ?: "N/A",
        description = description?.en ?: "N/A",
        imageUrl = image?.large ?: "",
        currentPrice = marketData?.currentPrice?.usd?.let { price ->
            val formatted = if (price < 0.01) {
                val rounded = (price * 100000000).toInt() / 100000000.0
                "$rounded"
            } else {
                val rounded = (price * 100).toInt() / 100.0
                "$rounded"
            }
            "$formatted USD"
        } ?: "N/A",
        priceChange24h = marketData?.priceChangePercentage24hInCurrency?.usd?.let { change ->
            val rounded = (change * 100).toInt() / 100.0
            "$rounded"
        } ?: "N/A",
        isPositive = marketData?.priceChangePercentage24hInCurrency?.usd?.let {
            it >= 0
        } ?: false)
}