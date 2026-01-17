package com.oguzhan.shared.ui

import androidx.compose.runtime.Composable
import com.oguzhan.shared.ui.screen.navigation.CtNavHost

@Composable
actual fun PlatformApp(startDestination: Any) {
    CtNavHost()
}

