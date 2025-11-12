package com.oguzhan.cryptotracker.di

import com.oguzhan.shared.core.data.firebase.FirebaseAuthApi
import com.oguzhan.shared.core.data.firebase.FirebaseFireStoreApi
import com.oguzhan.shared.core.data.local.AppDatabase
import com.oguzhan.shared.core.data.local.CoinDao
import com.oguzhan.shared.core.data.local.getDatabaseBuilder
import com.oguzhan.shared.core.data.remote.CoinApi
import com.oguzhan.shared.core.data.remote.EndPoint
import com.oguzhan.shared.core.data.remote.CoinKtorApi
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
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.auth.auth
import dev.gitlive.firebase.firestore.firestore
import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.header
import io.ktor.http.ContentType
import io.ktor.http.ContentType.Application.Json
import io.ktor.http.HttpHeaders
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.core.module.Module
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module


val appModule = module {

    // Common dependencies
    single {
        HttpClient {
            // Her isteğe varsayılan olarak eklenecek header'lar için doğru blok
            defaultRequest {
                url(EndPoint.BASE_URL)
                header(HttpHeaders.ContentType, ContentType.Application.Json)
                header("x-cg-demo-api-key", "CG-24STUw4CMoMhduH36TgTagAR")
            }


            // JSON serileştirme için
            install(ContentNegotiation) {
                json(Json {
                    prettyPrint = true
                    isLenient = true
                    ignoreUnknownKeys = true
                })
            }

            // Ağ trafiğini loglamak için
            install(Logging) {
                level = LogLevel.ALL
                logger = object : Logger {
                    override fun log(message: String) {
                        println("Ktor => $message")
                    }
                }
            }
        }
    }

    singleOf(::AuthRepositoryImpl) { bind<AuthRepository>() }
    singleOf(::CoinRepositoryImpl) { bind<CoinRepository>() }

    single<CoinApi> { CoinKtorApi(get()) }

    single { FirebaseAuthApi(firebaseAuth = Firebase.auth) }
    single { FirebaseFireStoreApi(Firebase.firestore, get()) }


    viewModelOf(::AuthViewModel)
    viewModelOf(::FavoriteViewModel)
    viewModelOf(::CryptoListScreenViewModel)
    viewModelOf(::CryptoListDetailViewModel)

    //usecases
    singleOf(::GetCoinByIdUseCase)

    singleOf(::GetCoinListUseCases)
    singleOf(::GetFavoriteCoinListUseCase)
    singleOf(::SearchCoinListUseCases)
    singleOf(::SetFavoriteCoinUseCase)

    // Database
    single<AppDatabase> { getDatabaseBuilder() }

    single<CoinDao> {
        get<AppDatabase>().coinDao()
    }

}


