/*
package com.oguzhan.shared.core.data.firebase

import com.oguzhan.shared.core.data.remote.model.FavoriteCoinFirebaseModel
import com.skydoves.sandwich.ApiResponse
import dev.gitlive.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.collectLatest

class FirebaseFireStoreApi constructor(
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

        runCatching {
            favoriteRef.set(favoriteCoin)
        }.onFailure {
            trySend(ApiResponse.Failure.Exception(Throwable("${it.message}")))
        }.onSuccess {
            trySend(ApiResponse.Success(true))
        }
    }

    fun removeFavoriteCoin(coinId: String) = callbackFlow<ApiResponse<Boolean>> {
        val userId = firebaseAuthApi.currentUser()?.uid ?: run {
            trySend(ApiResponse.Failure.Exception(Throwable("User is not logged in")))
            close()
            return@callbackFlow
        }

        val favoriteRef =
            fireStore.collection("users").document(userId).collection("favorites").document(coinId)

        runCatching {
            favoriteRef.delete()
        }.onFailure {
            trySend(ApiResponse.Failure.Exception(Throwable("${it.message}")))
        }.onSuccess {
            trySend(ApiResponse.Success(true))
        }
        awaitClose()
    }

    fun getFavoriteCoins() = callbackFlow<ApiResponse<List<FavoriteCoinFirebaseModel>>> {
        val userId = firebaseAuthApi.currentUser()?.uid ?: run {
            trySend(ApiResponse.Failure.Exception(Throwable("User is not logged in")))
            close()
            return@callbackFlow
        }

        val favoriteRef = fireStore.collection("users").document(userId).collection("favorites")

        favoriteRef.snapshots.collectLatest { snapshot ->
            val coins = snapshot.documents.mapNotNull { doc ->
                val id = doc.get<String?>("coinId")
                val name = doc.get<String?>("name")
                if (id != null && name != null) {
                    FavoriteCoinFirebaseModel(coinId = id, name = name)
                } else null
            }
            trySend(ApiResponse.Success(coins)).isSuccess
        }
        awaitClose()
    }
}



*/
