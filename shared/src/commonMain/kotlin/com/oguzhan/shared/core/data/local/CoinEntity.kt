package com.oguzhan.shared.core.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable


@Serializable
@Entity(tableName = "coins")
data class CoinEntity(
    @PrimaryKey val id: String,
    val name: String?,
    val symbol: String?
)
