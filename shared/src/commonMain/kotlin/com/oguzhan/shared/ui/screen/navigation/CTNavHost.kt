package com.oguzhan.shared.ui.screen.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.oguzhan.shared.ui.screen.auth.authNavGraph
import com.oguzhan.shared.ui.screen.coin.mainNavGraph
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

//Parent Route for Auth
@Serializable
object Auth

@Serializable
object Login

@Serializable
object Register

@Composable
fun CtNavHost(
    navController: NavHostController, startDestination: Any = Auth
) {

    NavHost(
        modifier = Modifier,
        navController = navController,
        startDestination = startDestination
    ) {
        authNavGraph(navController)
        mainNavGraph(navController)
    }
}

