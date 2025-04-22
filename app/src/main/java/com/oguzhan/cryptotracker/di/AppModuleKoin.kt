package com.oguzhan.cryptotracker.di

import androidx.room.Room
import androidx.work.Configuration
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.google.firebase.Firebase
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.firestore
import com.oguzhan.cryptotracker.MainViewModel
import com.oguzhan.cryptotracker.common.PriceUpdateWorker
import com.oguzhan.shared.core.data.local.AppDatabase
import com.oguzhan.shared.core.data.local.CoinDao
import com.oguzhan.shared.core.data.remote.BASE_URL
import com.oguzhan.shared.core.data.remote.CoinApi
import com.oguzhan.shared.core.data.firebase.FirebaseAuthApi
import com.oguzhan.shared.core.data.firebase.FirebaseFireStoreApi
import com.oguzhan.shared.core.data.repository.AuthRepositoryImpl
import com.oguzhan.shared.core.data.repository.CoinRepositoryImpl
import com.oguzhan.shared.core.domain.repository.AuthRepository
import com.oguzhan.shared.core.domain.repository.CoinRepository
import com.oguzhan.shared.core.domain.usecase.GetCoinByIdUseCase
import com.oguzhan.shared.core.domain.usecase.GetCoinListUseCases
import com.oguzhan.shared.core.domain.usecase.GetFavoriteCoinListUseCase
import com.oguzhan.shared.core.domain.usecase.SearchCoinListUseCases
import com.oguzhan.shared.core.domain.usecase.SetFavoriteCoinUseCase
import com.oguzhan.shared.ui.screen.auth.AuthViewModel
import com.oguzhan.shared.ui.screen.coin.detail.CryptoListDetailViewModel
import com.oguzhan.shared.ui.screen.coin.favourite.FavoriteViewModel
import com.oguzhan.shared.ui.screen.coin.list.CryptoListScreenViewModel
import com.skydoves.sandwich.retrofit.adapters.ApiResponseCallAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.workmanager.dsl.worker
import org.koin.androidx.workmanager.dsl.workerOf
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.coroutines.EmptyCoroutineContext.get

private val logging = HttpLoggingInterceptor().apply {
    level = HttpLoggingInterceptor.Level.BODY
}

val appModule = module {

    single {
        FirebaseApp.initializeApp(androidContext())
    }


    singleOf(::AuthRepositoryImpl) { bind<AuthRepository>() }
    singleOf(::CoinRepositoryImpl) { bind<CoinRepository>() }



    single { FirebaseAuthApi(firebaseAuth = FirebaseAuth.getInstance()) }
    single { FirebaseFireStoreApi(Firebase.firestore, get()) }

    singleOf(::AuthRepositoryImpl) {
        bind<AuthRepository>()
    }
    singleOf(::CoinRepositoryImpl) {
        bind<CoinRepository>()
    }

    viewModelOf(::MainViewModel)
    viewModelOf(::AuthViewModel)
    viewModelOf(::CryptoListDetailViewModel)
    viewModelOf(::FavoriteViewModel)
    viewModelOf(::CryptoListScreenViewModel)


    //usecases
    singleOf(::GetCoinByIdUseCase)
    singleOf(::GetCoinListUseCases)
    singleOf(::GetFavoriteCoinListUseCase)
    singleOf(::SearchCoinListUseCases)
    singleOf(::SetFavoriteCoinUseCase)

    single<CoinApi> {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(get())
            .addCallAdapterFactory(ApiResponseCallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(CoinApi::class.java)

    }

    single<OkHttpClient> {
        OkHttpClient.Builder()
            .addInterceptor { chain ->
                val request = chain.request().newBuilder()
                    .addHeader("Content-Type", "application/json; charset=utf-8")
                    .addHeader("x-cg-demo-api-key", "CG-24STUw4CMoMhduH36TgTagAR")
                    .build()
                chain.proceed(request)
            }
            .addInterceptor(ChuckerInterceptor(context = androidContext()))
            .addInterceptor(logging)
            .build()
    }



    worker { PriceUpdateWorker(get(), get(), get()) }
    workerOf(::PriceUpdateWorker, options = {
        Configuration.Builder()
            .setMinimumLoggingLevel(android.util.Log.DEBUG)
            .build()
    })



    single<AppDatabase> {
        Room.databaseBuilder(
            androidContext(),
            AppDatabase::class.java,
            "crypto_database"
        ).fallbackToDestructiveMigration()
            .build()
    }

    single<CoinDao> {
        get<AppDatabase>().coinDao()
    }
}

