package com.oguzhan.shared.ui

import androidx.compose.runtime.Composable
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
expect fun PlatformApp()

@Composable
@Preview
fun App() {
    PlatformApp()
}