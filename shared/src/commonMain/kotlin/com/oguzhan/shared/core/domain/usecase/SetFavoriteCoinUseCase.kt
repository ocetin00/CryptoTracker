/*
package com.oguzhan.shared.core.domain.usecase

import com.oguzhan.shared.core.Result
import com.oguzhan.shared.core.domain.repository.CoinRepository
import com.skydoves.sandwich.messageOrNull
import com.skydoves.sandwich.suspendOnFailure
import com.skydoves.sandwich.suspendOnSuccess
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.lastOrNull

class SetFavoriteCoinUseCase constructor(
    private val repository: CoinRepository,
) {
    operator fun invoke(
        coinId: String,
        name: String?,
        isFavorite: Boolean
    ): Flow<Result<Boolean>> = flow {
        repository.setFavoriteCoin(coinId, name, isFavorite).lastOrNull()?.let { response ->
            response
                .suspendOnFailure {
                    emit(Result.Error(message = this.messageOrNull))
                }.suspendOnSuccess {
                    emit(Result.Success(data = this.data))
                }
        }
    }
}*/
