package com.oguzhan.cryptotracker.common

import android.content.Context
import android.util.Log
import androidx.work.Constraints
import androidx.work.CoroutineWorker
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.WorkerParameters
import com.oguzhan.shared.core.domain.repository.CoinRepository
import com.skydoves.sandwich.getOrThrow
import java.util.concurrent.TimeUnit

class PriceUpdateWorker constructor(
    appContext: Context,
    workerParams: WorkerParameters,
    private var coinRepository: CoinRepository
) : CoroutineWorker(appContext, workerParams) {

    override suspend fun doWork(): Result {
        return try {
            Log.d("PriceUpdateWorker", "doWork started")
            coinRepository.fetchAndStoreCoins().getOrThrow()
            Result.success()
        } catch (e: Exception) {
            Log.e("PriceUpdateWorker", "Exception: ${e.message}")
            Result.failure()
        }
    }
}


fun schedulePriceUpdates(context: Context) {
    // Android requires minimum 15 minutes for PeriodicWorkRequest
    val workRequest = PeriodicWorkRequestBuilder<PriceUpdateWorker>(
        15, TimeUnit.MINUTES // Minimum interval for periodic work
    )
        .setConstraints(
            Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .build()
        )
        .build()

    WorkManager.getInstance(context).enqueueUniquePeriodicWork(
        "price_update_work",
        ExistingPeriodicWorkPolicy.KEEP, // KEEP existing work instead of CANCEL_AND_REENQUEUE
        workRequest
    )

    Log.d("PriceUpdateWorker", "Scheduled periodic price updates every 15 minutes")
}

/**
 * For testing purposes - schedules a one-time immediate work request
 * Use this for debugging/testing instead of waiting 15 minutes
 */
fun scheduleImmediatePriceUpdate(context: Context) {
    val workRequest = androidx.work.OneTimeWorkRequestBuilder<PriceUpdateWorker>()
        .setConstraints(
            Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .build()
        )
        .build()

    WorkManager.getInstance(context).enqueue(workRequest)
    Log.d("PriceUpdateWorker", "Scheduled immediate one-time price update")
}

/**
 * Cancels all scheduled price update work
 */
fun cancelPriceUpdates(context: Context) {
    WorkManager.getInstance(context).cancelUniqueWork("price_update_work")
    Log.d("PriceUpdateWorker", "Cancelled all price update work")
}




