package com.oguzhan.shared.ui

import androidx.compose.runtime.Composable
import com.oguzhan.cryptotracker.di.appModule
import com.oguzhan.shared.ui.screen.navigation.CtNavHost
import org.koin.compose.KoinApplication

@Composable
actual fun PlatformApp(startDestination: Any) {
    KoinApplication(
        application = {
            modules(
                appModule
            )
        },
        content = {
            CtNavHost()
        }
    )
}

