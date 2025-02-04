package com.oguzhan.cryptotracker

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.rememberNavController
import com.oguzhan.cryptotracker.common.schedulePriceUpdates
import com.oguzhan.cryptotracker.ui.screen.auth.Auth
import com.oguzhan.cryptotracker.ui.screen.coin.Main
import com.oguzhan.cryptotracker.ui.screen.navigation.CtNavHost
import com.oguzhan.cryptotracker.ui.theme.CryptoTrackerTheme
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewModel: MainViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {

        installSplashScreen().setKeepOnScreenCondition {
            //show splash until isLoggedIn is not null
            viewModel.mainState.value.showSplash
        }

        super.onCreate(savedInstanceState)
        schedulePriceUpdates(this)
        enableEdgeToEdge()
        setContent {
            val state = viewModel.mainState.collectAsStateWithLifecycle()
            val navController = rememberNavController()
            Log.d("MainActivity", "isLoggedIn: ${state.value.isLoggedIn}")
            state.value.isLoggedIn?.let {
                if (state.value.isLoggedIn != null) {
                    CryptoTrackerTheme {
                        CtNavHost(
                            navController,
                            startDestination = if (state.value.isLoggedIn == true) Main else Auth
                        )
                    }
                }
            }
        }
    }
}
