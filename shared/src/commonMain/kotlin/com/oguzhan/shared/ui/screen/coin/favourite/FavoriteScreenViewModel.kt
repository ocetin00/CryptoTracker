package com.oguzhan.shared.ui.screen.coin.favourite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.oguzhan.shared.core.Result
import com.oguzhan.shared.core.data.remote.model.FavoriteCoinUiModel
import com.oguzhan.shared.core.domain.usecase.GetFavoriteCoinListUseCase
import com.oguzhan.shared.core.domain.usecase.SetFavoriteCoinUseCase
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class FavoriteScreenState(
    val isLoading: Boolean = false,
    val favoriteCoins: List<FavoriteCoinUiModel> = emptyList()
)

sealed interface FavoriteScreenEffect {
    data class ShowToast(val message: String) : FavoriteScreenEffect
}

class FavoriteViewModel constructor(
    private val getFavoriteCoinListUseCase: GetFavoriteCoinListUseCase,
    private val setFavoriteCoinUseCase: SetFavoriteCoinUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(FavoriteScreenState())
    val state: StateFlow<FavoriteScreenState> = _state.asStateFlow()

    private val _effect = MutableSharedFlow<FavoriteScreenEffect>()
    val effect: SharedFlow<FavoriteScreenEffect> = _effect.asSharedFlow()

    init {
        getFavoriteCoins()
    }

    private fun getFavoriteCoins() {
        viewModelScope.launch {
            getFavoriteCoinListUseCase().collectLatest { result ->
                when (result) {
                    is Result.Loading -> {
                        _state.update { it.copy(isLoading = true) }
                    }

                    is Result.Success -> {
                        _state.update {
                            it.copy(
                                isLoading = false,
                                favoriteCoins = result.data ?: listOf()
                            )
                        }
                    }

                    is Result.Error -> {
                        _state.update { it.copy(isLoading = false) }
                        _effect.emit(FavoriteScreenEffect.ShowToast(result.message ?: "Error"))
                    }
                }
            }
        }
    }

    fun removeFavoriteCoin(coinId: String) {
        viewModelScope.launch {
            setFavoriteCoinUseCase(coinId, null, isFavorite = false)
                .collectLatest { result ->
                    when (result) {
                        is Result.Loading -> Unit

                        is Result.Success -> {
                            getFavoriteCoins()
                            _effect.emit(FavoriteScreenEffect.ShowToast("Removed Favorite"))
                        }

                        is Result.Error -> {
                            _effect.emit(
                                FavoriteScreenEffect.ShowToast(
                                    result.message ?: "Error"
                                )
                            )
                        }
                    }
                }
        }
    }


}

