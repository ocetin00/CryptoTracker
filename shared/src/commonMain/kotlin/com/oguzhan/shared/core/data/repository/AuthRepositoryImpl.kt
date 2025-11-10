/*
package com.oguzhan.shared.core.data.repository

import com.oguzhan.shared.core.Result
import com.oguzhan.shared.core.data.firebase.FirebaseAuthApi
import com.oguzhan.shared.core.domain.repository.AuthRepository
import dev.gitlive.firebase.auth.FirebaseUser
import kotlinx.coroutines.flow.Flow

class AuthRepositoryImpl constructor(private val firebaseAuthApi: FirebaseAuthApi) :
    AuthRepository {
    override fun login(email: String, password: String): Flow<Result<FirebaseUser>> {
        return firebaseAuthApi.login(email, password)
    }

    override fun register(email: String, password: String): Flow<Result<FirebaseUser>> {
        return firebaseAuthApi.register(email, password)
    }

    override fun isLoggedIn(): Boolean {
        return firebaseAuthApi.isLoggedIn()
    }

    override suspend fun signOut(): Boolean {
        return firebaseAuthApi.signOut()
    }
}*/
