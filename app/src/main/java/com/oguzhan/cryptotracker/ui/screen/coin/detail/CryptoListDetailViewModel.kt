package com.oguzhan.cryptotracker.ui.screen.coin.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.oguzhan.cryptotracker.common.Result
import com.oguzhan.cryptotracker.domain.model.CoinDetailUiModel
import com.oguzhan.cryptotracker.domain.repository.CoinRepository
import com.oguzhan.cryptotracker.domain.usecase.GetCoinByIdUseCase
import com.oguzhan.cryptotracker.domain.usecase.SetFavoriteCoinUseCase
import com.oguzhan.cryptotracker.ui.screen.coin.CryptoListDetail
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import javax.inject.Inject


data class CryptoListDetailScreenState(
    val isLoading: Boolean = false,
    val coinDetailModel: CoinDetailUiModel? = null,
    val refreshInterval: Int = 0,
    val isFavorite: Boolean = false,
    val errorMessage: String? = null
)

sealed interface CryptoListDetailScreenEffect {
    data class ShowToast(val message: String) : CryptoListDetailScreenEffect
}


@HiltViewModel
class CryptoListDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val coinRepository: CoinRepository,
    private val getCoinByIdUseCase: GetCoinByIdUseCase,
    private val setFavoriteCoinUseCase: SetFavoriteCoinUseCase,
) :
    ViewModel() {

    private val cryptoListDetail = savedStateHandle.toRoute<CryptoListDetail>()

    private val _state: MutableStateFlow<CryptoListDetailScreenState> =
        MutableStateFlow(CryptoListDetailScreenState())
    val state: StateFlow<CryptoListDetailScreenState> = _state.asStateFlow()

    private val _effect = MutableSharedFlow<CryptoListDetailScreenEffect>()
    val effect: SharedFlow<CryptoListDetailScreenEffect> = _effect.asSharedFlow()


    init {
        getCoinDetail(cryptoListDetail.id)
    }

    private var refreshJob: Job? = null

    fun startPriceRefresh(interval: Int) {
        refreshJob?.cancel()
        refreshJob = viewModelScope.launch {
            while (isActive) {
                getCoinByIdUseCase(cryptoListDetail.id).collectLatest {
                    when (val result = it) {
                        is Result.Success -> {
                            _state.update {
                                it.copy(
                                    isLoading = false,
                                    coinDetailModel = result.data,
                                )
                            }
                            _effect.emit(CryptoListDetailScreenEffect.ShowToast("Price updated"))
                        }

                        is Result.Error -> {
                            _effect.emit(
                                CryptoListDetailScreenEffect.ShowToast(
                                    result.message ?: "An error occurred"
                                )
                            )
                        }

                        is Result.Loading -> Unit
                    }
                }
                delay(interval * 1000L)
            }
        }
    }

    fun toggleFavorite() {
        viewModelScope.launch {
            val coinDetailModel = state.value.coinDetailModel ?: return@launch
            setFavoriteCoinUseCase(
                coinDetailModel.id,
                coinDetailModel.name,
                !state.value.isFavorite
            )
                .collect {
                    when (val result = it) {
                        is Result.Success -> {
                            _state.value = state.value.copy(
                                isLoading = false,
                                isFavorite = !state.value.isFavorite
                            )
                            CryptoListDetailScreenEffect.ShowToast(
                                if (state.value.isFavorite) "Added to favorites" else "Removed from favorites"
                            )
                        }

                        is Result.Error -> {
                            _state.value = state.value.copy(isLoading = false)

                            _effect.emit(
                                CryptoListDetailScreenEffect.ShowToast(
                                    result.message ?: "An error occurred"
                                )
                            )
                        }

                        is Result.Loading -> {
                            _state.value = state.value.copy(isLoading = true)
                        }
                    }
                }
        }
    }

    private fun getCoinDetail(id: String) {
        viewModelScope.launch {
            getCoinByIdUseCase(id).collectLatest {
                when (val result = it) {
                    is Result.Success -> {
                        _state.update {
                            it.copy(
                                isLoading = false,
                                coinDetailModel = result.data
                            )
                        }
                    }

                    is Result.Error -> {
                        _state.update { it.copy(isLoading = false) }
                        _effect.emit(
                            CryptoListDetailScreenEffect.ShowToast(
                                result.message ?: "An error occurred"
                            )
                        )
                    }

                    is Result.Loading -> {
                        _state.update { it.copy(isLoading = true) }
                    }
                }
            }
        }

    }
}