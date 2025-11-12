package com.oguzhan.shared.ui

import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import com.oguzhan.cryptotracker.di.appModule
import com.oguzhan.shared.di.platformModule
import com.oguzhan.shared.ui.screen.navigation.CtNavHost
import org.koin.compose.KoinApplication

@Composable
actual fun PlatformApp(startDestination: Any) {
    KoinApplication(
        application = {
            modules(
                platformModule(),
                appModule
            )
        },
        content = {
            val navController = rememberNavController()
            CtNavHost(navController = navController, startDestination = startDestination)
        }
    )
}

