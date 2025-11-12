package com.oguzhan.shared.core.domain.usecase

import com.oguzhan.cryptotracker.domain.model.FavoriteCoinMapper.toUiModel
import com.oguzhan.shared.core.Result
import com.oguzhan.shared.core.data.remote.model.FavoriteCoinUiModel
import com.oguzhan.shared.core.domain.repository.CoinRepository
import com.skydoves.sandwich.getOrNull
import com.skydoves.sandwich.isSuccess
import com.skydoves.sandwich.messageOrNull
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetFavoriteCoinListUseCase constructor(
    private val repository: CoinRepository,
) {
    operator fun invoke(): Flow<Result<List<FavoriteCoinUiModel>?>> =
        repository.getFavoriteCoins().map {
            if (it.isSuccess) {
                Result.Success(it.getOrNull()?.map { it.toUiModel() })
            } else {
                Result.Error(message = it.messageOrNull)
            }
        }

}
