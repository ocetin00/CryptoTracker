
package com.oguzhan.shared.core.domain.usecase

import com.oguzhan.shared.core.model.CoinUiModel
import com.oguzhan.shared.core.util.Result
import com.oguzhan.shared.core.domain.repository.CoinRepository
import com.oguzhan.shared.core.mapper.CoinMapper.toUiModel
import com.skydoves.sandwich.message
import com.skydoves.sandwich.suspendMapSuccess
import com.skydoves.sandwich.suspendOnFailure
import com.skydoves.sandwich.suspendOnSuccess
import kotlinx.coroutines.flow.flow

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
}
