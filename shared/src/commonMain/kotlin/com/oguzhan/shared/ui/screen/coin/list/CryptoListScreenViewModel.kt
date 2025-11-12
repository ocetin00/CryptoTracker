package com.oguzhan.shared.ui.screen.coin.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.oguzhan.shared.core.Result
import com.oguzhan.shared.core.domain.model.CoinUiModel
import com.oguzhan.shared.core.domain.repository.AuthRepository
import com.oguzhan.shared.core.domain.usecase.GetCoinListUseCases
import com.oguzhan.shared.core.domain.usecase.SearchCoinListUseCases
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class CryptoListScreenState(
    val query: String = "",
    val isLoading: Boolean = false,
    val coinList: List<CoinUiModel> = emptyList(),
)

sealed interface CryptoListScreenEffect {
    data class ShowSnackBar(val message: String) : CryptoListScreenEffect
    data object NavigateToAuth : CryptoListScreenEffect
}


class CryptoListScreenViewModel constructor(
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
                        _state.update { it.copy(isLoading = true) }
                    }

                    is Result.Success -> {
                        println("success: ${result.data}")
                        _state.update {
                            it.copy(
                                isLoading = false,
                                coinList = result.data
                            )
                        }
                    }

                    is Result.Error -> {
                        println("error: ${result.message}")
                        _state.update { it.copy(isLoading = false) }
                        _effect.emit(CryptoListScreenEffect.ShowSnackBar(result.message ?: "Error"))
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
