package com.oguzhan.cryptotracker.ui.screen.coin

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.oguzhan.cryptotracker.ui.screen.auth.Auth
import com.oguzhan.cryptotracker.ui.screen.coin.detail.CoinDetailScreenRoute
import com.oguzhan.cryptotracker.ui.screen.coin.favourite.FavoriteScreenRoute
import com.oguzhan.cryptotracker.ui.screen.coin.list.CryptoListScreenRoute
import kotlinx.serialization.Serializable

//Parent Route for Main
@Serializable
object Main

@Serializable
object CryptoList

@Serializable
data class CryptoListDetail(val id: String)

@Serializable
object FavoriteCoin


fun NavGraphBuilder.mainNavGraph(navController: NavController) {
    navigation<Main>(
        startDestination = CryptoList
    ) {
        composable<CryptoList> {
            CryptoListScreenRoute(
                viewModel = hiltViewModel(),
                onNavigateToDetail = { id ->
                    navController.navigate(
                        CryptoListDetail(id)
                    )
                },
                onNavigateToFavorite = {
                    navController.navigate(FavoriteCoin)
                },
                onNavigateToAuth = {
                    navController.navigate(Auth)
                }
            )
        }
        composable<CryptoListDetail> { backStackEntry ->
            CoinDetailScreenRoute(
                onNavigateToBack = {
                    navController.popBackStack()
                },
                viewModel = hiltViewModel(),
            )
        }
        composable<FavoriteCoin> {
            FavoriteScreenRoute(
                viewModel = hiltViewModel(),
                onNavigateToBack = {
                    navController.popBackStack()
                }
            )
        }
    }
}