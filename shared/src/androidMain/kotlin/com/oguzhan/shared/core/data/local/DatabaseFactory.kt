package com.oguzhan.shared.core.data.local

import android.content.Context
import androidx.room.Room
import org.koin.mp.KoinPlatform.getKoin

actual fun getDatabaseBuilder(): AppDatabase {
    val context = getKoin().get<Context>()
    val dbFile = context.getDatabasePath("crypto_database.db")
    return getRoomDatabase(
        Room.databaseBuilder<AppDatabase>(
            context = context,
            name = dbFile.absolutePath
        )
    )
}

