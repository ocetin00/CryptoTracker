package com.oguzhan.cryptotracker.domain.usecase

import com.oguzhan.cryptotracker.common.Result
import com.oguzhan.cryptotracker.domain.model.CoinUiModel
import com.oguzhan.cryptotracker.domain.repository.CoinRepository
import com.oguzhan.cryptotracker.mapper.CoinMapper.toUiModel
import com.skydoves.sandwich.mapSuccess
import com.skydoves.sandwich.message
import com.skydoves.sandwich.suspendOnFailure
import com.skydoves.sandwich.suspendOnSuccess
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

class GetCoinListUseCases @Inject constructor(
    private val coinRepository: CoinRepository,
) {
    operator fun invoke(): Flow<Result<List<CoinUiModel>>> = flow<Result<List<CoinUiModel>>> {
        coinRepository.fetchAndStoreCoins().mapSuccess {
            this.mapNotNull { it?.toUiModel() }
        }.suspendOnSuccess {
            val result = data
            emit(Result.Success(result))

        }.suspendOnFailure {
            emit(Result.Error(message = message()))
        }
    }.onStart {
        emit(Result.Loading)
    }
}