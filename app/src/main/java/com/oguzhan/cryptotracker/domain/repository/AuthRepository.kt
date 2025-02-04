package com.oguzhan.cryptotracker.domain.repository

import com.google.firebase.auth.FirebaseUser
import com.oguzhan.cryptotracker.common.Result
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    fun login(email: String, password: String): Flow<Result<FirebaseUser>>
    fun register(email: String, password: String): Flow<Result<FirebaseUser>>
    fun isLoggedIn(): Boolean
    fun signOut(): Boolean
}