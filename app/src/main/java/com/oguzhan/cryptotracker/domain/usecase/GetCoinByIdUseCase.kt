package com.oguzhan.cryptotracker.domain.usecase

import com.oguzhan.shared.core.Result
import com.oguzhan.cryptotracker.domain.model.CoinDetailUiModel
import com.oguzhan.cryptotracker.domain.repository.CoinRepository
import com.oguzhan.cryptotracker.mapper.CoinDetailMapper.toUiModel
import com.skydoves.sandwich.mapSuccess
import com.skydoves.sandwich.message
import com.skydoves.sandwich.suspendOnFailure
import com.skydoves.sandwich.suspendOnSuccess
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

class GetCoinByIdUseCase @Inject constructor(
    private val coinRepository: CoinRepository,
) {
    operator fun invoke(id: String): Flow<Result<CoinDetailUiModel?>> =
        flow {
            coinRepository.getCoinById(id).mapSuccess {
                this?.toUiModel()
            }.suspendOnSuccess {
                emit(Result.Success(data))
            }.suspendOnFailure {
                emit(Result.Error(message = message()))
            }
        }.onStart {
            emit(Result.Loading)
        }
}