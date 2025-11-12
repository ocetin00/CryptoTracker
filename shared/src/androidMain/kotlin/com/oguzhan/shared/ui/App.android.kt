package com.oguzhan.shared.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.rememberNavController
import com.oguzhan.shared.di.androidPlatformModule
import com.oguzhan.cryptotracker.di.appModule
import com.oguzhan.shared.ui.screen.navigation.CtNavHost
import org.koin.compose.KoinApplication

@Composable
actual fun PlatformApp() {
    val context = LocalContext.current

    KoinApplication(
        application = {
            modules(
                androidPlatformModule(context),
                appModule
            )
        },
        content = {
            val navController = rememberNavController()
            CtNavHost(navController = navController)
        }
    )
}

