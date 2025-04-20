package com.oguzhan.cryptotracker

import android.app.Application
import com.google.firebase.FirebaseApp
import com.oguzhan.cryptotracker.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.androidx.workmanager.koin.workManagerFactory
import org.koin.core.context.startKoin
import org.koin.core.context.startKoin

class CTApp : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@CTApp)
            androidLogger()
            workManagerFactory()
            modules(appModule)
        }

    }
}