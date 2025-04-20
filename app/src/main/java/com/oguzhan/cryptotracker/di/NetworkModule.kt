/*
package com.oguzhan.cryptotracker.di

import android.content.Context
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.oguzhan.cryptotracker.data.remote.BASE_URL
import com.oguzhan.cryptotracker.data.remote.CoinApi
import com.skydoves.sandwich.retrofit.adapters.ApiResponseCallAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    private val logging = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }


    @Singleton
    @Provides
    fun provideOkHttpClient(
        @ApplicationContext context: Context
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor { chain ->
                val request = chain.request().newBuilder()
                    .addHeader("Content-Type", "application/json; charset=utf-8")
                    .addHeader("x-cg-demo-api-key", "CG-24STUw4CMoMhduH36TgTagAR")
                    .build()
                chain.proceed(request)
            }
            .addInterceptor(ChuckerInterceptor(context))
            .addInterceptor(logging)
            .build()
    }


    @Singleton
    @Provides
    fun provideCoinApi(
        okHttpClient: OkHttpClient
    ): CoinApi {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addCallAdapterFactory(ApiResponseCallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(CoinApi::class.java)
    }

}*/
