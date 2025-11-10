/*
package com.oguzhan.shared.core.domain.usecase

import com.oguzhan.shared.core.domain.model.CoinUiModel
import com.oguzhan.shared.core.domain.repository.CoinRepository
import com.oguzhan.shared.core.mapper.CoinMapper.toUiModel

class SearchCoinListUseCases constructor(
    private val coinRepository: CoinRepository,
) {
    suspend operator fun invoke(query: String): List<CoinUiModel> {
        return coinRepository.searchCoins(query).map { it.toUiModel() }
    }

}*/
