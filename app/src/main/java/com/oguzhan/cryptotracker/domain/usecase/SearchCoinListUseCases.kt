package com.oguzhan.cryptotracker.domain.usecase

import com.oguzhan.cryptotracker.domain.model.CoinUiModel
import com.oguzhan.cryptotracker.domain.repository.CoinRepository
import com.oguzhan.cryptotracker.mapper.CoinMapper.toUiModel
import javax.inject.Inject

class SearchCoinListUseCases @Inject constructor(
    private val coinRepository: CoinRepository,
) {
    suspend operator fun invoke(query: String): List<CoinUiModel> {
        return coinRepository.searchCoins(query).map { it.toUiModel() }
    }

}