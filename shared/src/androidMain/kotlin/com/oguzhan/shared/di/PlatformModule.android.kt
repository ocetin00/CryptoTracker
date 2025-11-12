package com.oguzhan.shared.di

import android.content.Context
import com.oguzhan.cryptotracker.MainViewModel
import com.oguzhan.cryptotracker.common.PriceUpdateWorker
import org.koin.androidx.workmanager.dsl.workerOf
import org.koin.core.module.Module
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module


fun androidPlatformModule(context: Context): Module = module {
    single<Context> { context }
    viewModelOf(::MainViewModel)
    workerOf(::PriceUpdateWorker)
}

actual fun platformModule(): Module {
    TODO("Not yet implemented")
}