/*
package com.oguzhan.shared.core.data.remote

import com.oguzhan.shared.core.data.remote.model.CoinDetailRemoteModel
import com.oguzhan.shared.core.data.remote.model.CoinRemoteModel
import com.skydoves.sandwich.ApiResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface CoinApi {

    @GET(COIN_LIST)
    suspend fun getCoinList(): ApiResponse<List<CoinRemoteModel?>>

    @GET(COIN_DETAIL)
    suspend fun getCoinDetail(@Path("id") coinId: String): ApiResponse<CoinDetailRemoteModel?>

}*/
