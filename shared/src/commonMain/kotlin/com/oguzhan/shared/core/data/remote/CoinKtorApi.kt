package com.oguzhan.shared.core.data.remote

import com.oguzhan.shared.core.data.remote.model.CoinDetailRemoteModel
import com.oguzhan.shared.core.data.remote.model.CoinRemoteModel
import com.skydoves.sandwich.ApiResponse
import com.skydoves.sandwich.ktor.getApiResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.parameter
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders

class CoinKtorApi(private val client: HttpClient) : CoinApi {
    override suspend fun getCoinById(coinId: String): ApiResponse<CoinDetailRemoteModel?> =
        client.getApiResponse<CoinDetailRemoteModel?>(EndPoint.COIN_DETAIL) {
            header(HttpHeaders.ContentType, ContentType.Application.Json)
            parameter("id", coinId)
        }


    override suspend fun getCoins(): ApiResponse<List<CoinRemoteModel>?> =
        client.getApiResponse<List<CoinRemoteModel>?>(EndPoint.COIN_LIST) {
            header(HttpHeaders.ContentType, ContentType.Application.Json)
        }

}


interface CoinApi {
    suspend fun getCoinById(coinId: String): ApiResponse<CoinDetailRemoteModel?>
    suspend fun getCoins(): ApiResponse<List<CoinRemoteModel>?>
}