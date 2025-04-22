package com.oguzhan.shared.core.mapper

import com.oguzhan.shared.core.domain.model.CoinUiModel
import com.oguzhan.shared.core.data.local.CoinEntity
import com.oguzhan.shared.core.data.remote.model.CoinRemoteModel

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
