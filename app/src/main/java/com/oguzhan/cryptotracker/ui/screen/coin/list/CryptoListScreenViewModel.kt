package com.oguzhan.cryptotracker.ui.screen.coin.list

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.oguzhan.cryptotracker.common.Result
import com.oguzhan.cryptotracker.domain.model.CoinUiModel
import com.oguzhan.cryptotracker.domain.repository.AuthRepository
import com.oguzhan.cryptotracker.domain.usecase.GetCoinListUseCases
import com.oguzhan.cryptotracker.domain.usecase.SearchCoinListUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class CryptoListScreenState(
    val query: String = "",
    val isLoading: Boolean = false,
    val coinList: List<CoinUiModel> = emptyList(),
)

sealed interface CryptoListScreenEffect {
    data class ShowSnackBar(val message: String) : CryptoListScreenEffect
    data object NavigateToAuth : CryptoListScreenEffect
}


@HiltViewModel
class CryptoListScreenViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val getCoinListUseCases: GetCoinListUseCases,
    private val searchCoinListUseCases: SearchCoinListUseCases,

    ) :
    ViewModel() {

    private val _state: MutableStateFlow<CryptoListScreenState> =
        MutableStateFlow(CryptoListScreenState())
    val state: StateFlow<CryptoListScreenState> = _state.asStateFlow()

    private val _effect = MutableSharedFlow<CryptoListScreenEffect>()
    val effect: SharedFlow<CryptoListScreenEffect> = _effect.asSharedFlow()

    init {
        fetchAndStoreCoins()
    }


    private fun fetchAndStoreCoins() {
        viewModelScope.launch {
            getCoinListUseCases().collectLatest {
                when (val result = it) {
                    is Result.Loading -> {
                        Log.d("CryptoListScreenViewModel", "Loading")
                        _state.update { it.copy(isLoading = true) }
                    }

                    is Result.Success -> {
                        _state.update {
                            it.copy(
                                isLoading = false,
                                coinList = result.data
                            )
                        }
                        Log.d("CryptoListScreenViewModel", "Success")
                    }

                    is Result.Error -> {
                        _state.update { it.copy(isLoading = false) }
                        _effect.emit(CryptoListScreenEffect.ShowSnackBar(result.message ?: "Error"))
                        Log.d("CryptoListScreenViewModel", "Error")
                    }
                }
            }
        }
    }

    private fun searchCoins(query: String) {
        viewModelScope.launch {
            val result = searchCoinListUseCases(query)
            _state.update { it.copy(coinList = result) }
        }
    }

    fun updateQuery(query: String) {
        _state.update { it.copy(query = query) }
        searchCoins(query)
    }

    fun signOut() {
        viewModelScope.launch {
            val result = authRepository.signOut()
            if (result) {
                _effect.emit(CryptoListScreenEffect.NavigateToAuth)
            } else {
                _effect.emit(CryptoListScreenEffect.ShowSnackBar("Sign out failed"))
            }
        }
    }
}