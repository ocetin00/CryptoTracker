package com.oguzhan.cryptotracker


import android.app.Application


class CTApp : Application() {

    override fun onCreate() {
        super.onCreate()


     /*   startKoin {
            androidContext(this@CTApp)
            androidLogger()
            workManagerFactory()
            modules(appModule)
        }
*/
    }
}