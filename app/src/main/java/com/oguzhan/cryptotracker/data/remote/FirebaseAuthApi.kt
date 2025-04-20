package com.oguzhan.cryptotracker.data.remote

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.oguzhan.shared.core.Result
import com.skydoves.sandwich.ApiResponse
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject

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
        authListener.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val user = firebaseAuth.currentUser
                if (user != null) {
                    trySend(Result.Success(user))
                } else {
                    trySend(Result.Error("User is null"))
                }
            } else {
                trySend(Result.Error(task.exception?.message ?: "", task.exception))
            }
        }
        awaitClose { authListener.isComplete }
    }

    fun register(email: String, password: String) = callbackFlow<Result<FirebaseUser>> {
        trySend(Result.Loading)
        val authListener = firebaseAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val user = firebaseAuth.currentUser
                    if (user != null) {
                        trySend(Result.Success(user))
                    } else {
                        trySend(Result.Error("User is null"))
                    }
                } else {
                    trySend(
                        Result.Error(
                            task.exception?.message ?: "Unknown error",
                            task.exception
                        )
                    )
                }
            }

        awaitClose { authListener.isComplete }
    }

    fun signOut(): Boolean {
        firebaseAuth.signOut()
        return if (firebaseAuth.currentUser == null) {
            true
        } else {
            false
        }
    }
}
