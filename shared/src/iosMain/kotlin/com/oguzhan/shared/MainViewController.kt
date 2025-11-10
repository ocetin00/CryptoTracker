package com.oguzhan.shared

import androidx.compose.ui.window.ComposeUIViewController
import com.oguzhan.shared.ui.App


fun MainViewController() = ComposeUIViewController {
    App()
}

actual fun getPlatform(): Platform {
    TODO("Not yet implemented")
}