package com.oguzhan.shared.ui

import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import com.oguzhan.shared.ui.screen.navigation.CtNavHost

@Composable
actual fun PlatformApp(startDestination: Any) {
    val navController = rememberNavController()
    CtNavHost(navController = navController, startDestination = startDestination)
}

