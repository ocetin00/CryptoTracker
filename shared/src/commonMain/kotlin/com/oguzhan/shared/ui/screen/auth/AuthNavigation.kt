package com.oguzhan.shared.ui.screen.auth

import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.oguzhan.shared.ui.screen.navigation.Auth
import com.oguzhan.shared.ui.screen.navigation.Login
import com.oguzhan.shared.ui.screen.navigation.Main
import com.oguzhan.shared.ui.screen.navigation.Register
import org.koin.compose.viewmodel.koinViewModel




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
