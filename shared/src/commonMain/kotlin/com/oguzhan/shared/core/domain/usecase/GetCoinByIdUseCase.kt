package com.oguzhan.shared.core.domain.usecase

import com.oguzhan.cryptotracker.domain.model.CoinDetailUiModel
import com.oguzhan.shared.core.Result
import com.oguzhan.shared.core.domain.repository.CoinRepository
import com.oguzhan.shared.core.mapper.CoinDetailMapper.toUiModel
import com.skydoves.sandwich.mapSuccess
import com.skydoves.sandwich.message
import com.skydoves.sandwich.suspendMapSuccess
import com.skydoves.sandwich.suspendOnFailure
import com.skydoves.sandwich.suspendOnSuccess
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onStart

class GetCoinByIdUseCase constructor(
    private val coinRepository: CoinRepository,
) {
    operator fun invoke(id: String): Flow<Result<CoinDetailUiModel?>> =
        flow {
            coinRepository.getCoinById(id)
                .suspendMapSuccess {
                    this?.toUiModel()
                }
                .suspendOnSuccess {
                    emit(Result.Success(data))
                }.suspendOnFailure {
                    emit(Result.Error(message = message()))
                }

        }.onStart {
            emit(Result.Loading)
        }
}