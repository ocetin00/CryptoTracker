package com.oguzhan.cryptotracker.mapper

import com.oguzhan.cryptotracker.data.remote.model.CoinDetailRemoteModel
import com.oguzhan.cryptotracker.domain.model.CoinDetailUiModel

object CoinDetailMapper {

    fun CoinDetailRemoteModel.toUiModel() = CoinDetailUiModel(
        id = id,
        name = name ?: "N/A",
        hashingAlgorithm = hashingAlgorithm ?: "N/A",
        description = description?.en ?: "N/A",
        imageUrl = image?.large ?: "",
        currentPrice = marketData?.currentPrice?.usd?.let { price ->
            if (price < 0.01) String.format("%.8f USD", price)
            else String.format("%.2f USD", price)
        } ?: "N/A",
        priceChange24h = marketData?.priceChangePercentage24hInCurrency?.usd.let { change ->
            "%.2f".format(change) ?: "N/A"
        },
        isPositive = marketData?.priceChangePercentage24hInCurrency?.usd?.let {
            it >= 0
        } ?: false)
}