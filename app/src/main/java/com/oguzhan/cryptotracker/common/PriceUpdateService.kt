package com.oguzhan.cryptotracker.common

import android.content.Context
import android.util.Log
import androidx.work.Constraints
import androidx.work.CoroutineWorker
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.ExistingWorkPolicy
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.WorkerParameters
import com.oguzhan.cryptotracker.common.Result
import com.oguzhan.cryptotracker.domain.repository.CoinRepository
import com.skydoves.sandwich.isSuccess
import java.util.concurrent.TimeUnit

class PriceUpdateWorker constructor(
    appContext: Context,
    workerParams: WorkerParameters,
    private var coinRepository: CoinRepository
) : CoroutineWorker(appContext, workerParams) {

    override suspend fun doWork(): Result {
        return try {
            Log.d("PriceUpdateWorker", "doWork started")
            val result = coinRepository.fetchAndStoreCoins().isSuccess
            if (result) {
                Log.d("PriceUpdateWorker", "Success: Data updated")
                Result.success()
            } else {
                Log.d("PriceUpdateWorker", "Error: Data not updated")
                Result.failure()
            }
        } catch (e: Exception) {
            Log.e("PriceUpdateWorker", "Exception: ${e.message}")
            Result.failure()
        }
    }
}


fun schedulePriceUpdates(context: Context) {
    val workRequest = PeriodicWorkRequestBuilder<PriceUpdateWorker>(15, TimeUnit.MINUTES)
        .setConstraints(
            Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .build()
        )
        .build()

    WorkManager.getInstance(context).enqueueUniquePeriodicWork(
        "price_update_work",
        ExistingPeriodicWorkPolicy.CANCEL_AND_REENQUEUE,
        workRequest
    )
}




