package com.oguzhan.shared.ui.screen.auth

import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.oguzhan.shared.ui.screen.coin.Main
import kotlinx.serialization.Serializable
import org.koin.compose.viewmodel.koinViewModel

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
                viewModel = koinViewModel(),
                onBackToLoginClick = { navController.navigate(Login) }
            )
        }
    }
}
