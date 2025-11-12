package com.oguzhan.shared.ui.screen.coin

import androidx.compose.material3.Text
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.oguzhan.shared.ui.screen.auth.Auth
import com.oguzhan.shared.ui.screen.coin.detail.CoinDetailScreenRoute
import com.oguzhan.shared.ui.screen.coin.favourite.FavoriteScreenRoute
import com.oguzhan.shared.ui.screen.coin.list.CryptoListScreenRoute
import kotlinx.serialization.Serializable
import org.koin.compose.viewmodel.koinViewModel

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
