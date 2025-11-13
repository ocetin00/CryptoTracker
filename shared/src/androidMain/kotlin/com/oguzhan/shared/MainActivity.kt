package com.oguzhan.shared

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.work.WorkManager
import com.oguzhan.cryptotracker.MainViewModel
import com.oguzhan.cryptotracker.common.scheduleImmediatePriceUpdate
import com.oguzhan.cryptotracker.common.schedulePriceUpdates
import com.oguzhan.shared.ui.App
import com.oguzhan.shared.ui.screen.navigation.Auth
import com.oguzhan.shared.ui.screen.navigation.Main
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : ComponentActivity() {
    private val viewModel: MainViewModel by viewModel()
    override fun onCreate(savedInstanceState: Bundle?) {


        installSplashScreen().setKeepOnScreenCondition {
            //show splash until isLoggedIn is not null
            viewModel.mainState.value.showSplash
        }

        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        // Schedule periodic updates (every 15 minutes)
        schedulePriceUpdates(this)
        
        // For testing: Schedule an immediate update to see it working right away
        scheduleImmediatePriceUpdate(this)

        setContent {
            val state = viewModel.mainState.collectAsStateWithLifecycle()
            Log.d("MainActivity", "isLoggedIn: ${state.value.isLoggedIn}")
            state.value.isLoggedIn?.let {
                if (state.value.isLoggedIn != null) {
                    App(startDestination = if (state.value.isLoggedIn == true) Main else Auth)
                }
            }
        }


    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("MainActivity", "WorkManager cancelled")
        WorkManager.getInstance(this).cancelUniqueWork("price_update_work")
    }
}