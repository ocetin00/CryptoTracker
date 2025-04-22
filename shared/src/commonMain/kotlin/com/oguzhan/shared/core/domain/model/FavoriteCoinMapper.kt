package com.oguzhan.cryptotracker.domain.model

import com.oguzhan.shared.core.data.remote.model.FavoriteCoinFirebaseModel
import com.oguzhan.shared.core.data.remote.model.FavoriteCoinUiModel

object FavoriteCoinMapper {
    fun FavoriteCoinUiModel.toFirebaseModel(): FavoriteCoinFirebaseModel {
        return FavoriteCoinFirebaseModel(
            coinId = coinId,
            name = name
        )
    }

    fun FavoriteCoinFirebaseModel.toUiModel(): FavoriteCoinUiModel {
        return FavoriteCoinUiModel(
            coinId = coinId,
            name = name
        )
    }
}