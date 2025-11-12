package com.oguzhan.shared.ui

import androidx.compose.runtime.Composable
import androidx.navigation.NavDestination
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
expect fun PlatformApp(startDestination: Any)

@Composable
@Preview
fun App(startDestination: Any) {
    PlatformApp(startDestination)
}