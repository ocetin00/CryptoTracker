/*
package com.oguzhan.shared.core.domain.usecase

import com.oguzhan.shared.core.domain.model.CoinUiModel
import com.oguzhan.shared.core.Result
import com.oguzhan.shared.core.domain.repository.CoinRepository
import com.oguzhan.shared.core.mapper.CoinMapper.toUiModel
import com.skydoves.sandwich.message
import com.skydoves.sandwich.suspendMapSuccess
import com.skydoves.sandwich.suspendOnFailure
import com.skydoves.sandwich.suspendOnSuccess
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onStart

class GetCoinListUseCases constructor(
    private val coinRepository: CoinRepository,
) {
    operator fun invoke() = flow<Result<List<CoinUiModel>>> {
        coinRepository.fetchAndStoreCoins()
            .suspendMapSuccess {
                this?.map { it.toUiModel() }
            }.suspendOnSuccess {
                val result: List<CoinUiModel>? = data
                emit(Result.Success(result ?: emptyList()))
            }.suspendOnFailure {
                emit(Result.Error(message = message()))
            }
    }
}*/
