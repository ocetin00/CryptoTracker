/*
package com.oguzhan.shared.core.data.repository

import com.oguzhan.shared.core.data.local.CoinDao
import com.oguzhan.shared.core.data.local.CoinEntity
import com.oguzhan.shared.core.data.remote.CoinApi
import com.oguzhan.shared.core.domain.repository.CoinRepository
import com.oguzhan.shared.core.data.remote.model.CoinDetailRemoteModel
import com.oguzhan.shared.core.data.remote.model.CoinRemoteModel
import com.oguzhan.shared.core.data.remote.model.FavoriteCoinFirebaseModel
import com.oguzhan.shared.core.data.firebase.FirebaseFireStoreApi
import com.oguzhan.shared.core.mapper.CoinMapper.toCoinEntity
import com.skydoves.sandwich.ApiResponse
import com.skydoves.sandwich.getOrNull
import com.skydoves.sandwich.mapSuccess
import kotlinx.coroutines.flow.Flow


class CoinRepositoryImpl constructor(
    private val api: CoinApi,
    private val coinDao: CoinDao,
    private val firebaseFireStoreApi: FirebaseFireStoreApi
) : CoinRepository {
    override suspend fun fetchAndStoreCoins(): ApiResponse<List<CoinRemoteModel>?> {
        val response = api.getCoins()
        //Log.d("CoinRepositoryImpl", "fetchAndStoreCoins: $response")
        val entities = response.mapSuccess {
            this?.map { it.toCoinEntity() }
        }.getOrNull() ?: emptyList()
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
        return api.getCoinById(coinId)
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
*/
