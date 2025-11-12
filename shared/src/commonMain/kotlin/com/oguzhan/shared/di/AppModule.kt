/*
package com.oguzhan.shared.di


import androidx.room.Room

import com.oguzhan.shared.core.data.local.AppDatabase
import com.oguzhan.shared.core.data.local.CoinDao
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.auth.FirebaseAuth
import dev.gitlive.firebase.auth.auth
import dev.gitlive.firebase.firestore.FirebaseFirestore
import dev.gitlive.firebase.firestore.firestore


@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Singleton
    @Provides
    fun provideFirebaseAuth(): FirebaseAuth {
        return Firebase.auth
    }

    @Singleton
    @Provides
    fun provideFirebaseFireStoreDB(): FirebaseFirestore {
        return Firebase.firestore
    }

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            AppDatabase::class.java,
            "crypto_database"
        ).fallbackToDestructiveMigration()
            .build()
    }

    @Singleton
    @Provides
    fun provideCoinDao(database: AppDatabase): CoinDao {
        return database.coinDao()
    }


    @Provides
    @Singleton
    fun provideDevCtrlWorkManager(@ApplicationContext context: Context): WorkManager =
        WorkManager.getInstance(context)
}
*/
