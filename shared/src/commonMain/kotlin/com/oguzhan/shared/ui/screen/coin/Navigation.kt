package com.oguzhan.shared.ui.screen.coin

import androidx.compose.material3.Text
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.oguzhan.shared.ui.screen.coin.detail.CoinDetailScreenRoute
import com.oguzhan.shared.ui.screen.coin.favourite.FavoriteScreenRoute
import com.oguzhan.shared.ui.screen.coin.list.CryptoListScreenRoute
import com.oguzhan.shared.ui.screen.navigation.Auth
import com.oguzhan.shared.ui.screen.navigation.CryptoList
import com.oguzhan.shared.ui.screen.navigation.CryptoListDetail
import com.oguzhan.shared.ui.screen.navigation.FavoriteCoin
import com.oguzhan.shared.ui.screen.navigation.Main
import kotlinx.serialization.Serializable
import org.koin.compose.viewmodel.koinViewModel



fun NavGraphBuilder.mainNavGraph(navController: NavController) {
    navigation<Main>(
        startDestination = CryptoList
    ) {

        composable<CryptoList> {
            CryptoListScreenRoute(
                viewModel = koinViewModel(),
                onNavigateToDetail = { id ->
                    navController.navigate(
                        CryptoListDetail(id)
                    )
                },
                onNavigateToFavorite = {
                    navController.navigate(FavoriteCoin)
                },
                onNavigateToAuth = {
                    navController.navigate(Auth) {
                        popUpTo(0) { inclusive = true }
                    }
                }
            )
        }

        composable<CryptoListDetail> { backStackEntry ->
            CoinDetailScreenRoute(
                onNavigateToBack = {
                    navController.popBackStack()
                },
                viewModel = koinViewModel(),
            )
        }
        composable<FavoriteCoin> {
            FavoriteScreenRoute(
                viewModel = koinViewModel(),
                onNavigateToBack = {
                    navController.popBackStack()
                }
            )
        }
    }

}
