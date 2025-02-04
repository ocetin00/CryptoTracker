package com.oguzhan.cryptotracker.data.repository

import com.google.firebase.auth.FirebaseUser
import com.oguzhan.cryptotracker.common.Result
import com.oguzhan.cryptotracker.data.remote.FirebaseAuthApi
import com.oguzhan.cryptotracker.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(private val firebaseAuthApi: FirebaseAuthApi) :
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

    override fun signOut(): Boolean {
        return firebaseAuthApi.signOut()
    }
}