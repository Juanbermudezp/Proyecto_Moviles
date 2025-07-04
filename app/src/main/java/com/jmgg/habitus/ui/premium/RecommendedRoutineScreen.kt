package com.jmgg.habitus.ui.premium

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.Text
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.jmgg.habitus.HabitusApp
import com.jmgg.habitus.models.Habit
import com.jmgg.habitus.ui.premium.components.RoutineCard
import kotlinx.coroutines.launch

@Composable
fun RecommendedRoutinesScreen(navController: NavController) {
    val currentUser = HabitusApp.authViewModel.currentUser.collectAsState().value
    val scrollState = rememberScrollState()


    if (currentUser == null || !currentUser.isPremium) {
        Box(modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF0F172A)),
            contentAlignment = Alignment.Center
        ) {
            Text(
                "Solo para usuarios premium.",
                color = Color(0xFFF8FAFC)
            )
        }
        return
    }

    val routines = listOf(
        "sleep" to "Dormir mejor y más rápido",
        "mental" to "Salud mental y relajación",
        "fitness" to "Estilo de vida activo"
    )

    Column(modifier = Modifier
        .fillMaxSize()
        .background(Color(0xFF0F172A))
        .padding(24.dp)
        .verticalScroll(scrollState),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            "Rutinas personalizadas",
            style = MaterialTheme.typography.headlineSmall.copy(color = Color(0xFFF8FAFC))
        )

        routines.forEach { (id, title) ->
            RoutineCard(
                title = title,
                description = "Presiona para más detalles",
                onSelect = {
                    navController.navigate("routineDetail/$id")
                }
            )
        }
    }
}
