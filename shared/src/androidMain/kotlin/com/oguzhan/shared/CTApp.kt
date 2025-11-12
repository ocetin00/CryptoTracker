package com.oguzhan.shared

import android.app.Application
import com.oguzhan.cryptotracker.di.appModule
import com.oguzhan.shared.di.androidPlatformModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.androidx.workmanager.koin.workManagerFactory
import org.koin.core.context.startKoin

class CTApp : Application() {

    override fun onCreate() {
        super.onCreate()

        // Initialize Koin for Android
        startKoin {
            androidContext(this@CTApp)
            androidLogger()
            workManagerFactory()
            modules(
                androidPlatformModule(this@CTApp),
                appModule
            )
        }
    }
}