package com.jmgg.habitus.ui.main.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.Composable

@Composable
fun PremiumBottomNavBar(
    currentRoute: String?,
    onNavigateToStats: () -> Unit,
    onNavigateToRoutines: () -> Unit
) {
    NavigationBar {
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
