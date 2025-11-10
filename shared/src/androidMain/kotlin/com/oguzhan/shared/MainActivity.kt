package com.oguzhan.shared

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.viewmodel.compose.viewModel
import com.oguzhan.cryptotracker.MainViewModel
import com.oguzhan.shared.ui.App

class MainActivity : ComponentActivity() {
    //   private val viewModel: MainViewModel by viewModel()
    override fun onCreate(savedInstanceState: Bundle?) {


        //  Log.i("Login Activity", "Hello from shared module: " + (Greeting().greet()))

        /*
                installSplashScreen().setKeepOnScreenCondition {
                    //show splash until isLoggedIn is not null
                    viewModel.mainState.value.showSplash
                }*/

        super.onCreate(savedInstanceState)
        //  schedulePriceUpdates(this)
        enableEdgeToEdge()

        setContent {
            App()
        }
        /* setContent {


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
         }*/
    }

    /* override fun onDestroy() {
         super.onDestroy()
         Log.d("MainActivity", "WorkManager cancelled")
         WorkManager.getInstance(this).cancelUniqueWork("price_update_work")
     }*/
}