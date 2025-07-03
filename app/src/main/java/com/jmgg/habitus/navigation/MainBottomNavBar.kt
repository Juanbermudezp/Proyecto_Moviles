package com.jmgg.habitus.ui.main.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.Composable

@Composable
fun MainBottomNavBar(
    isPremium: Boolean,
    currentRoute: String?,
    onNavigateToHome: () -> Unit,
    onNavigateToStats: () -> Unit,
    onNavigateToRoutines: () -> Unit,
) {

    NavigationBar {
        NavigationBarItem(
            selected = currentRoute == "main",
            onClick = onNavigateToHome,
            icon = {
                Icon(
                    imageVector = Icons.Default.Home,
                    contentDescription = "Inicio"
                )
            },
            label = { Text("Inicio") }
        )
        if (isPremium) {
            NavigationBarItem(
                selected = currentRoute == "stats",
                onClick = onNavigateToStats,
                icon = {
                    Icon(
                        imageVector = Icons.Default.BarChart,
                        contentDescription = "Estadísticas"
                    )
                },
                label = { Text("Estadísticas") }
            )

            NavigationBarItem(
                selected = currentRoute == "routines",
                onClick = onNavigateToRoutines,
                icon = {
                    Icon(
                        imageVector = Icons.Default.Star,
                        contentDescription = "Recomendaciones"
                    )
                },
                label = { Text("Rutinas") }
            )
        }
    }
}
