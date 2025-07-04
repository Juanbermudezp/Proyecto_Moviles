package com.jmgg.habitus.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.jmgg.habitus.HabitusApp
import com.jmgg.habitus.ui.auth.*

@Composable
fun AuthPagerPage(
    onLoggedIn: () -> Unit
) {
    var isLoginMode by remember { mutableStateOf(true) }
    val viewModel = HabitusApp.authViewModel

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF0F172A))
            .padding(24.dp)
    ) {
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
}
