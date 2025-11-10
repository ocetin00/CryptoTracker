package com.oguzhan.cryptotracker.di

import com.oguzhan.shared.core.data.local.AppDatabase
import com.oguzhan.shared.core.data.local.CoinDao
import com.oguzhan.shared.core.data.remote.CoinApi
import com.oguzhan.shared.core.domain.repository.CoinRepository
import com.oguzhan.shared.core.domain.usecase.GetCoinByIdUseCase
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
                header(HttpHeaders.ContentType, ContentType.Application.Json)
                header("x-cg-demo-api-key", "CG-24STUw4CMoMhduH36TgTagAR")
            }

            // JSON serileştirme için
            install(ContentNegotiation) {
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

    //singleOf(::AuthRepositoryImpl) { bind<AuthRepository>() }
    //singleOf(::CoinRepositoryImpl) { bind<CoinRepository>() }


 //   single { FirebaseAuthApi(firebaseAuth = Firebase.auth) }
  //  single { FirebaseFireStoreApi(Firebase.firestore, get()) }


 //   viewModelOf(::AuthViewModel)
  //  viewModelOf(::FavoriteViewModel)

/*

    //usecases
    singleOf(::GetCoinByIdUseCase)
    singleOf(::GetCoinListUseCases)
    singleOf(::GetFavoriteCoinListUseCase)
    singleOf(::SearchCoinListUseCases)
    singleOf(::SetFavoriteCoinUseCase)

    single<CoinDao> {
        get<AppDatabase>().coinDao()
    }
*/

}