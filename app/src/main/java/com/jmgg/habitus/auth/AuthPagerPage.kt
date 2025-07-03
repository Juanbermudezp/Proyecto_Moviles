package com.jmgg.habitus.auth

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.jmgg.habitus.HabitusApp
import com.jmgg.habitus.ui.auth.*

@Composable
fun AuthPagerPage(
    onLoggedIn: () -> Unit
) {
    var isLoginMode by remember { mutableStateOf(true) }
    val viewModel = HabitusApp.authViewModel

    if (isLoginMode) {
        LoginScreen(
            onLoginSuccess = { onLoggedIn() },
            onNavigateToRegister = {
                isLoginMode = false
                viewModel.clearError()

            }
        )
    } else {
        RegisterScreen(
            onRegistrationSuccess = { onLoggedIn() },
            onNavigateToLogin = {
                isLoginMode = true
                    viewModel.clearError()
            }
        )
    }
}
