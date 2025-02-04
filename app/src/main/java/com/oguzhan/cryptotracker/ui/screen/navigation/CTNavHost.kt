package com.oguzhan.cryptotracker.ui.screen.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.oguzhan.cryptotracker.ui.screen.auth.authNavGraph
import com.oguzhan.cryptotracker.ui.screen.coin.mainNavGraph

@Composable
fun CtNavHost(
    navController: NavHostController,
    startDestination: Any
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




