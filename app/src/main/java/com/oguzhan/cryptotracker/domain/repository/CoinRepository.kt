package com.oguzhan.cryptotracker.domain.repository

import com.oguzhan.cryptotracker.data.local.CoinEntity
import com.oguzhan.cryptotracker.data.remote.model.CoinDetailRemoteModel
import com.oguzhan.cryptotracker.data.remote.model.CoinRemoteModel
import com.oguzhan.cryptotracker.data.remote.model.FavoriteCoinFirebaseModel
import com.skydoves.sandwich.ApiResponse
import kotlinx.coroutines.flow.Flow

interface CoinRepository {
    fun getAllCoins(): Flow<List<CoinEntity>>
    suspend fun fetchAndStoreCoins(): ApiResponse<List<CoinRemoteModel?>>
    suspend fun searchCoins(query: String): List<CoinEntity>
    suspend fun getCoinById(coinId: String): ApiResponse<CoinDetailRemoteModel?>
    fun setFavoriteCoin(coinId: String, name: String?, isFavorite: Boolean): Flow<ApiResponse<Boolean>>
    fun getFavoriteCoins(): Flow<ApiResponse<List<FavoriteCoinFirebaseModel>>>
}