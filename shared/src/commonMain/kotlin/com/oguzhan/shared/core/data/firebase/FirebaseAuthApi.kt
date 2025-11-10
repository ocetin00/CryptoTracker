/*
package com.oguzhan.shared.core.data.firebase

import com.oguzhan.shared.core.Result
import dev.gitlive.firebase.auth.FirebaseAuth
import dev.gitlive.firebase.auth.FirebaseUser
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow

class FirebaseAuthApi constructor(private val firebaseAuth: FirebaseAuth) {

    fun isLoggedIn(): Boolean {
        return firebaseAuth.currentUser != null
    }

    fun currentUser(): FirebaseUser? {
        return firebaseAuth.currentUser
    }

    fun login(email: String, password: String) = callbackFlow<Result<FirebaseUser>> {
        trySend(Result.Loading)
        val authListener = firebaseAuth.signInWithEmailAndPassword(email, password)
        if (authListener.user != null) {
            val user = firebaseAuth.currentUser
            if (user != null) {
                trySend(Result.Success(user))
            } else {
                trySend(Result.Error("User is null"))

            }
        } else {
            trySend(Result.Error("Login failed"))

        }
        awaitClose()

    }

    fun register(email: String, password: String) = callbackFlow<Result<FirebaseUser>> {
        trySend(Result.Loading)
        val authListener = firebaseAuth.createUserWithEmailAndPassword(email, password)
        if (authListener.user != null) {
            val user = firebaseAuth.currentUser
            if (user != null) {
                trySend(Result.Success(user))
            } else {
                trySend(Result.Error("User is null"))
            }
        } else {
            trySend(Result.Error("Login failed"))
        }
        awaitClose()
    }

    suspend fun signOut(): Boolean {
        firebaseAuth.signOut()
        return firebaseAuth.currentUser == null
    }
}
*/
