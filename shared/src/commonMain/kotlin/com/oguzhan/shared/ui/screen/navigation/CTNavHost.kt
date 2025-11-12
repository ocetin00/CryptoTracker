package com.oguzhan.shared.ui.screen.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.oguzhan.shared.ui.screen.auth.Auth
import com.oguzhan.shared.ui.screen.auth.authNavGraph
import com.oguzhan.shared.ui.screen.coin.mainNavGraph

@Composable
fun CtNavHost(
    navController: NavHostController,
    startDestination: Any = Auth
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

