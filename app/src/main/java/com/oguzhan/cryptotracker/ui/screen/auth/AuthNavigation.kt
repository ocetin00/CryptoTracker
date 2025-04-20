package com.oguzhan.cryptotracker.ui.screen.auth

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.oguzhan.cryptotracker.ui.screen.coin.Main
import kotlinx.serialization.Serializable
import org.koin.androidx.compose.koinViewModel

//Parent Route for Auth
@Serializable
object Auth

@Serializable
object Login

@Serializable
object Register


fun NavGraphBuilder.authNavGraph(navController: NavController) {
    navigation<Auth>(
        startDestination = Login
    ) {
        composable<Login> {
            val parentEntry = navController.getBackStackEntry<Auth>()
            //TODO: should be shared
            LoginScreenRoute(
                viewModel = koinViewModel(),
                onNavigateSignUp = { navController.navigate(Register) },
                onNavigateToMain = {
                    navController.navigate(Main) {
                        popUpTo(Main) { inclusive = true }
                    }
                }
            )
        }

        composable<Register> {
            val parentEntry = navController.getBackStackEntry<Auth>()
            RegisterScreenRoute(
                viewModel =koinViewModel(),
                onBackToLoginClick = { navController.navigate(Login) }
            )
        }
    }
}