package com.oguzhan.cryptotracker.domain.usecase

import com.oguzhan.cryptotracker.common.Result
import com.oguzhan.cryptotracker.data.remote.model.FavoriteCoinUiModel
import com.oguzhan.cryptotracker.domain.model.FavoriteCoinMapper.toUiModel
import com.oguzhan.cryptotracker.domain.repository.CoinRepository
import com.skydoves.sandwich.getOrNull
import com.skydoves.sandwich.isSuccess
import com.skydoves.sandwich.messageOrNull
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetFavoriteCoinListUseCase @Inject constructor(
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