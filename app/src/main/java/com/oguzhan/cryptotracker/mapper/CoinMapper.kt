package com.oguzhan.cryptotracker.mapper

import com.oguzhan.shared.core.data.local.CoinEntity
import com.oguzhan.cryptotracker.data.remote.model.CoinRemoteModel
import com.oguzhan.cryptotracker.domain.model.CoinUiModel

object CoinMapper {

    fun CoinRemoteModel.toCoinEntity(): CoinEntity {
        return CoinEntity(
            id = this.id,
            name = this.name,
            symbol = this.symbol
        )
    }
    fun CoinRemoteModel.toUiModel(): CoinUiModel {
        return CoinUiModel(
            id = id,
            name = name,
            symbol = symbol
        )
    }

    fun CoinEntity.toUiModel(): CoinUiModel {
        return CoinUiModel(
            id = id,
            name = name,
            symbol = symbol
        )
    }
}
