package com.oguzhan.shared

import androidx.compose.ui.window.ComposeUIViewController
import com.oguzhan.shared.ui.App


fun MainViewController() = ComposeUIViewController(configure = {
    enforceStrictPlistSanityCheck = false // <== Here, this prevents the crash
}) {
    App()
}
