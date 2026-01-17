package com.oguzhan.shared.ui.screen.auth

import androidx.compose.animation.core.CubicBezierEasing
import androidx.compose.animation.core.TweenSpec
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.slideOutVertically
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

        composable<Login>(
            enterTransition = { slideInHorizontally(animationSpec = tween()) },
            popEnterTransition = { slideInHorizontally(animationSpec = tween()) },
        ) {
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

        composable<Register>(
            enterTransition = { slideInHorizontally(animationSpec = tween()) },
            popEnterTransition = { slideInHorizontally(animationSpec = tween()) },
        ) {
            val parentEntry = navController.getBackStackEntry<Auth>()
            RegisterScreenRoute(
                viewModel = koinViewModel(),
                onBackToLoginClick = { navController.navigate(Login) }
            )
        }
    }
}
