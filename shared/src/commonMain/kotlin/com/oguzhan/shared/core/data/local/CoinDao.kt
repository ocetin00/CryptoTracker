package com.oguzhan.shared.core.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface CoinDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCoins(coins: List<CoinEntity>)

    @Query("SELECT * FROM coins")
    fun getAllCoins(): Flow<List<CoinEntity>>

    @Query("SELECT * FROM coins WHERE name LIKE :query OR symbol LIKE :query")
    suspend fun searchCoins(query: String): List<CoinEntity>

    @Query("DELETE FROM coins")
    suspend fun clearAllCoins()
}
