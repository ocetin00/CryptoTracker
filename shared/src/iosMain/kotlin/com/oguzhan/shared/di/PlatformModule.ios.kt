package com.oguzhan.shared.di

import org.koin.core.module.Module
import org.koin.dsl.module

actual fun platformModule(): Module = module {
    // No platform-specific dependencies needed for iOS
}

