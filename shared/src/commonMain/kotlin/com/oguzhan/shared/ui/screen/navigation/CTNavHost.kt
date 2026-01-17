package com.oguzhan.shared.ui.screen.navigation

import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.runtime.Composable
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
import androidx.savedstate.serialization.SavedStateConfiguration
import com.oguzhan.shared.ui.screen.auth.LoginScreenRoute
import com.oguzhan.shared.ui.screen.auth.RegisterScreenRoute
import com.oguzhan.shared.ui.screen.coin.detail.CoinDetailScreenRoute
import com.oguzhan.shared.ui.screen.coin.favourite.FavoriteScreenRoute
import com.oguzhan.shared.ui.screen.coin.list.CryptoListScreenRoute
import kotlinx.serialization.Serializable
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic
import org.koin.compose.viewmodel.koinViewModel


//Parent Route for Main
@Serializable
object Main : NavKey

@Serializable
object CryptoList : NavKey

@Serializable
data class CryptoListDetail(val id: String) : NavKey

@Serializable
object FavoriteCoin : NavKey

//Parent Route for Auth
@Serializable
object Auth : NavKey

@Serializable
object Login : NavKey

@Serializable
object Register : NavKey

private val config = SavedStateConfiguration {
    serializersModule = SerializersModule {
        polymorphic(NavKey::class) {
            subclass(Main::class, Main.serializer())
            subclass(CryptoList::class, CryptoList.serializer())
            subclass(CryptoListDetail::class, CryptoListDetail.serializer())
            subclass(FavoriteCoin::class, FavoriteCoin.serializer())
            subclass(Auth::class, Auth.serializer())
            subclass(Login::class, Login.serializer())
            subclass(Register::class, Register.serializer())
        }
    }
}

@Composable
fun CtNavHost() {
    val backStack = rememberNavBackStack(config, Login)

    NavDisplay(
        backStack = backStack,
        onBack = { backStack.removeLastOrNull() },
        entryProvider = entryProvider {
            entry<Login>() {
                LoginScreenRoute(
                    viewModel = koinViewModel(),
                    onNavigateSignUp = {
                        backStack.add(Register)
                    },
                    onNavigateToMain = {
                        backStack.clear()
                        backStack.add(CryptoList)
                    }
                )
            }

            entry<Register>() {
                RegisterScreenRoute(
                    viewModel = koinViewModel(),
                    onBackToLoginClick = {
                        backStack.removeLastOrNull()
                    }
                )
            }

            entry<CryptoList>() {
                CryptoListScreenRoute(
                    viewModel = koinViewModel(),
                    onNavigateToDetail = { id ->
                        backStack.add(CryptoListDetail(id))
                    },
                    onNavigateToFavorite = {
                        backStack.add(FavoriteCoin)
                    },
                    onNavigateToAuth = {
                        backStack.clear()
                        backStack.add(Login)
                    }
                )
            }

            entry<CryptoListDetail>() {
                CoinDetailScreenRoute(
                    onNavigateToBack = {
                        backStack.removeLastOrNull()
                    },
                    viewModel = koinViewModel()
                )
            }

            entry<FavoriteCoin>() {
                FavoriteScreenRoute(
                    viewModel = koinViewModel(),
                    onNavigateToBack = {
                        backStack.removeLastOrNull()
                    }
                )
            }
        },
        transitionSpec = {
            // Slide in from right when navigating forward
            slideInHorizontally(
                initialOffsetX = { it },
                animationSpec = tween(1000)
            ) togetherWith slideOutHorizontally(
                targetOffsetX = { -it },
                animationSpec = tween(1000)
            )
        },
        popTransitionSpec = {
            // Slide in from left when navigating back
            slideInHorizontally(
                initialOffsetX = { -it },
                animationSpec = tween(1000)
            ) togetherWith slideOutHorizontally(
                targetOffsetX = { it },
                animationSpec = tween(1000)
            )
        },
        predictivePopTransitionSpec = {
            // Slide in from left when navigating back
            slideInHorizontally(
                initialOffsetX = { -it },
                animationSpec = tween(1000)
            ) togetherWith slideOutHorizontally(
                targetOffsetX = { it },
                animationSpec = tween(1000)
            )
        }
    )
}

