package com.oguzhan.shared.core.domain.repository

import com.oguzhan.shared.core.Result
import dev.gitlive.firebase.auth.FirebaseUser
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    fun login(email: String, password: String): Flow<Result<FirebaseUser>>
    fun register(email: String, password: String): Flow<Result<FirebaseUser>>
    fun isLoggedIn(): Boolean
    suspend fun signOut(): Boolean
}

