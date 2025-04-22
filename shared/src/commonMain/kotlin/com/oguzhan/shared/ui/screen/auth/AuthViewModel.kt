package com.oguzhan.shared.ui.screen.auth

import android.util.Patterns
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.oguzhan.shared.core.Result
import com.oguzhan.shared.core.domain.repository.AuthRepository
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

data class AuthState(
    val email: String = "",
    val password: String = "",
    val confirmPassword: String = "",
    val emailError: String? = null,
    val passwordError: String? = null,
    val confirmPasswordError: String? = null,
    val isLoading: Boolean = false,
    val isRegistered: Boolean = false,
    val isLoggedIn: Boolean = false,
    val error: String? = null
)

sealed interface AuthEffect {
    data class ShowSnackBar(val message: String) : AuthEffect
}

class AuthViewModel @Inject constructor(
    private val repository: AuthRepository
) : ViewModel() {

    private val _authState = MutableStateFlow(AuthState())
    val authState: StateFlow<AuthState> = _authState.asStateFlow()

    private val _authEffect = MutableSharedFlow<AuthEffect>()
    val authEffect: SharedFlow<AuthEffect> = _authEffect.asSharedFlow()

    fun updateEmail(email: String) {
        _authState.update { it.copy(email = email, emailError = null) }
    }

    fun updatePassword(password: String) {
        _authState.update { it.copy(password = password, passwordError = null) }
    }

    fun updateConfirmPassword(confirmPassword: String) {
        _authState.update {
            it.copy(
                confirmPassword = confirmPassword,
                confirmPasswordError = null
            )
        }
    }

    fun login() {
        if (!validateLogin()) return
        viewModelScope.launch {
            repository.login(_authState.value.email, _authState.value.password).collectLatest {
                when (val result = it) {
                    is Result.Loading -> _authState.update { it.copy(isLoading = true) }
                    is Result.Success -> {
                        _authState.update {
                            it.copy(
                                isLoading = false,
                                isLoggedIn = true
                            )
                        }
                        _authEffect.emit(AuthEffect.ShowSnackBar("SignIn Successful"))
                    }

                    is Result.Error -> {
                        _authState.update {
                            it.copy(isLoading = false)
                        }
                        _authEffect.emit(
                            AuthEffect.ShowSnackBar(
                                result.message ?: "An error occurred"
                            )
                        )
                    }
                }
            }
        }
    }

    fun register() {
        if (!validateRegister()) return
        viewModelScope.launch {
            repository.register(_authState.value.email, _authState.value.password).collectLatest {
                when (val result = it) {
                    is Result.Loading -> _authState.update { it.copy(isLoading = true) }
                    is Result.Success -> _authState.update {
                        it.copy(
                            isLoading = false,
                            isRegistered = true
                        )
                    }

                    is Result.Error -> _authState.update {
                        it.copy(error = result.message, isLoading = false)
                    }
                }
            }
        }
    }

    private fun validateLogin(): Boolean {
        val state = _authState.value
        var isValid = true

        if (state.email.isBlank() || !Patterns.EMAIL_ADDRESS.matcher(state.email).matches()) {
            _authState.update { it.copy(emailError = "Please enter a valid email") }
            isValid = false
        }

        if (state.password.length < 6) {
            _authState.update { it.copy(passwordError = "Password must be at least 6 characters") }
            isValid = false
        }

        return isValid
    }

    private fun validateRegister(): Boolean {
        var isValid = validateLogin()
        val state = _authState.value

        if (state.confirmPassword != state.password) {
            _authState.update { it.copy(confirmPasswordError = "Passwords do not match") }
            isValid = false
        }

        return isValid
    }

    fun resetState() {
        _authState.value = AuthState()
    }

}
