package com.oguzhan.cryptotracker.data.remote

import com.google.firebase.firestore.FirebaseFirestore
import com.oguzhan.cryptotracker.data.remote.model.FavoriteCoinFirebaseModel
import com.skydoves.sandwich.ApiResponse
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject

class FirebaseFireStoreApi @Inject constructor(
    private val fireStore: FirebaseFirestore,
    private val firebaseAuthApi: FirebaseAuthApi
) {

    fun addFavoriteCoin(coinId: String, coinName: String) = callbackFlow<ApiResponse<Boolean>> {
        val userId = firebaseAuthApi.currentUser()?.uid ?: run {
            trySend(ApiResponse.Failure.Exception(Throwable("User is not logged in")))
            close()
            return@callbackFlow
        }

        val favoriteRef =
            fireStore.collection("users").document(userId).collection("favorites").document(coinId)

        val favoriteCoin = mapOf(
            "coinId" to coinId,
            "name" to coinName
        )

        val task = favoriteRef.set(favoriteCoin)
        task.addOnSuccessListener {
            trySend(ApiResponse.Success(true))
            close()
        }.addOnFailureListener { e ->
            trySend(ApiResponse.Failure.Exception(Throwable(e.message)))

            close()
        }

        awaitClose { }
    }

    fun removeFavoriteCoin(coinId: String) = callbackFlow<ApiResponse<Boolean>> {

        val userId = firebaseAuthApi.currentUser()?.uid ?: run {
            trySend(ApiResponse.Failure.Exception(Throwable("User is not logged in")))

            close()
            return@callbackFlow
        }

        val favoriteRef =
            fireStore.collection("users").document(userId).collection("favorites").document(coinId)

        favoriteRef.delete().apply {
            addOnSuccessListener {
                trySend(ApiResponse.Success(true))
                close()
            }
            addOnFailureListener { e ->
                trySend(ApiResponse.Failure.Exception(Throwable(e.message)))
                close()
            }
        }

        awaitClose()
    }

    fun getFavoriteCoins() = callbackFlow<ApiResponse<List<FavoriteCoinFirebaseModel>>> {
        val userId = firebaseAuthApi.currentUser()?.uid ?: run {
            trySend(ApiResponse.Failure.Exception(Throwable("User is not logged in")))
            return@callbackFlow
        }

        val favoriteRef = fireStore.collection("users").document(userId).collection("favorites")

        val subscription = favoriteRef.addSnapshotListener { snapshot, error ->
            if (error != null) {
                trySend(ApiResponse.Failure.Exception(Throwable(error.message)))
                return@addSnapshotListener
            }

            val coins =
                snapshot?.documents?.mapNotNull { it.toObject(FavoriteCoinFirebaseModel::class.java) }
                    ?: emptyList()
            trySend(ApiResponse.Success(coins))
        }

        awaitClose { subscription.remove() }
    }
}



