package com.oguzhan.cryptotracker.di

import com.oguzhan.cryptotracker.data.local.CoinDao
import com.oguzhan.cryptotracker.data.remote.CoinApi
import com.oguzhan.cryptotracker.data.remote.FirebaseAuthApi
import com.oguzhan.cryptotracker.data.remote.FirebaseFireStoreApi
import com.oguzhan.cryptotracker.data.repository.AuthRepositoryImpl
import com.oguzhan.cryptotracker.data.repository.CoinRepositoryImpl
import com.oguzhan.cryptotracker.domain.repository.AuthRepository
import com.oguzhan.cryptotracker.domain.repository.CoinRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Singleton
    @Provides
    fun provideFirebaseAuth(firebaseAuthApi: FirebaseAuthApi): AuthRepository {
        return AuthRepositoryImpl(firebaseAuthApi)
    }

    @Singleton
    @Provides
    fun provideCoinRepository(
        coinApi: CoinApi,
        coinDao: CoinDao,
        firebaseFireStoreApi: FirebaseFireStoreApi
    ): CoinRepository {
        return CoinRepositoryImpl(coinApi, coinDao, firebaseFireStoreApi)
    }
}