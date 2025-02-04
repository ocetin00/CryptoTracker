package com.oguzhan.cryptotracker

import androidx.lifecycle.ViewModel
import com.oguzhan.cryptotracker.data.repository.AuthRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject


data class MainState(
    val isLoggedIn: Boolean? = null,
    val showSplash: Boolean = true
)

@HiltViewModel
class MainViewModel @Inject constructor(private val authRepositoryImpl: AuthRepositoryImpl) : ViewModel() {

    private val _mainState = MutableStateFlow(MainState())
    val mainState: StateFlow<MainState> = _mainState.asStateFlow()

    init {
        isLoggedIn()
    }

    private fun isLoggedIn() {
        _mainState.update {
            MainState(showSplash = false, isLoggedIn = authRepositoryImpl.isLoggedIn())
        }
    }
}