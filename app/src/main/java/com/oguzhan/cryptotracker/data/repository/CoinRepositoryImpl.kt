package com.oguzhan.cryptotracker.data.repository

import android.util.Log
import com.oguzhan.cryptotracker.data.local.CoinDao
import com.oguzhan.cryptotracker.data.local.CoinEntity
import com.oguzhan.cryptotracker.data.remote.CoinApi
import com.oguzhan.cryptotracker.data.remote.FirebaseFireStoreApi
import com.oguzhan.cryptotracker.data.remote.model.CoinDetailRemoteModel
import com.oguzhan.cryptotracker.data.remote.model.CoinRemoteModel
import com.oguzhan.cryptotracker.data.remote.model.FavoriteCoinFirebaseModel
import com.oguzhan.cryptotracker.domain.repository.CoinRepository
import com.oguzhan.cryptotracker.mapper.CoinMapper.toCoinEntity
import com.skydoves.sandwich.ApiResponse
import com.skydoves.sandwich.getOrNull
import com.skydoves.sandwich.mapSuccess
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class CoinRepositoryImpl @Inject constructor(
    private val api: CoinApi,
    private val coinDao: CoinDao,
    private val firebaseFireStoreApi: FirebaseFireStoreApi
) : CoinRepository {
    override suspend fun fetchAndStoreCoins(): ApiResponse<List<CoinRemoteModel?>> {
        val response = api.getCoinList()
        Log.d("CoinRepositoryImpl", "fetchAndStoreCoins: $response")
        val entities = response.mapSuccess {
            this.map { it?.toCoinEntity() }
        }.getOrNull()?.filterNotNull() ?: emptyList()
        coinDao.insertCoins(entities)
        return response
    }

    override fun getAllCoins(): Flow<List<CoinEntity>> {
        return coinDao.getAllCoins()
    }

    override suspend fun searchCoins(query: String): List<CoinEntity> {
        return coinDao.searchCoins("%$query%")
    }

    override suspend fun getCoinById(coinId: String): ApiResponse<CoinDetailRemoteModel?> {
        return api.getCoinDetail(coinId)
    }

    override fun setFavoriteCoin(
        coinId: String,
        name: String?,
        isFavorite: Boolean
    ): Flow<ApiResponse<Boolean>> {
        return if (isFavorite) {
            firebaseFireStoreApi.addFavoriteCoin(coinId, name ?: "")
        } else {
            firebaseFireStoreApi.removeFavoriteCoin(coinId)
        }
    }

    override fun getFavoriteCoins(): Flow<ApiResponse<List<FavoriteCoinFirebaseModel>>> {
        return firebaseFireStoreApi.getFavoriteCoins()
    }
}
