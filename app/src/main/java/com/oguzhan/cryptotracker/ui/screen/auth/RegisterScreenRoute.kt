package com.oguzhan.cryptotracker.ui.screen.auth

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.flow.collectLatest

@Composable
fun RegisterScreenRoute(
    viewModel: AuthViewModel,
    onBackToLoginClick: () -> Unit
) {
    val state by viewModel.authState.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.resetState()
        viewModel.authEffect.collectLatest { effect ->
            when (effect) {
                is AuthEffect.ShowSnackBar -> {
                    Toast.makeText(context, effect.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    if (state.isRegistered) {
        onBackToLoginClick()
    }

    RegisterScreen(
        authState = state,
        onBackToLoginClick = { onBackToLoginClick() },
        updateEmail = { viewModel.updateEmail(it) },
        updatePassword = { viewModel.updatePassword(it) },
        updateConfirmPassword = { viewModel.updateConfirmPassword(it) },
        register = {
            viewModel.register()
        },
    )
}

@Composable
fun RegisterScreen(
    authState: AuthState,
    onBackToLoginClick: () -> Unit,
    updateEmail: (String) -> Unit,
    updatePassword: (String) -> Unit,
    updateConfirmPassword: (String) -> Unit,
    register: () -> Unit
) {

    var passwordVisible by remember { mutableStateOf(false) }
    var passwordConfirmationVisible by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Register", fontSize = 24.sp, fontWeight = FontWeight.Bold)

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = authState.email,
            onValueChange = updateEmail,
            label = { Text("Email") },
            isError = authState.emailError != null,
            supportingText = { authState.emailError?.let { Text(it, color = Color.Red) } }
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = authState.password,
            onValueChange = updatePassword,
            label = { Text("Password") },
            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            trailingIcon = {
                val image =
                    if (passwordVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff
                if (authState.password.isNotEmpty()) {
                    IconButton(onClick = { passwordVisible = !passwordVisible }) {
                        Icon(
                            imageVector = image,
                            contentDescription = if (passwordVisible) "Hide password" else "Show password"
                        )
                    }
                }
            },
            isError = authState.passwordError != null,
            supportingText = { authState.passwordError?.let { Text(it, color = Color.Red) } }
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = authState.confirmPassword,
            onValueChange = updateConfirmPassword,
            label = { Text("Confirm Password") },
            visualTransformation = if (passwordConfirmationVisible) VisualTransformation.None else PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            trailingIcon = {
                val image =
                    if (passwordConfirmationVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff
                if (authState.confirmPassword.isNotEmpty()) {
                    IconButton(onClick = {
                        passwordConfirmationVisible = !passwordConfirmationVisible
                    }) {
                        Icon(
                            imageVector = image,
                            contentDescription = if (passwordConfirmationVisible) "Hide password" else "Show password"
                        )
                    }
                }

            },
            isError = authState.confirmPasswordError != null,
            supportingText = { authState.confirmPasswordError?.let { Text(it, color = Color.Red) } }
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = { register() },
            enabled = !authState.isLoading
        ) {
            if (authState.isLoading) {
                CircularProgressIndicator(modifier = Modifier.size(24.dp))
            } else {
                Text("Register")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Already have an account? Login",
            color = Color.Blue,
            modifier = Modifier.clickable { onBackToLoginClick() }
        )
    }
}


