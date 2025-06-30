package com.jmgg.habitus.ui.premium

import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.Text
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.jmgg.habitus.HabitusApp
import com.jmgg.habitus.models.Habit
import com.jmgg.habitus.ui.premium.components.RoutineCard
import kotlinx.coroutines.launch

@Composable
fun RecommendedRoutinesScreen(navController: NavController) {
    val currentUser = HabitusApp.authViewModel.currentUser.collectAsState().value

    if (currentUser == null || !currentUser.isPremium) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text("Solo para usuarios premium.")
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
        .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text("Rutinas personalizadas", style = MaterialTheme.typography.headlineSmall)

        routines.forEach { (id, title) ->
            RoutineCard(
                title = title,
                description = "Toca para más detalles",
                onSelect = {
                    navController.navigate("routineDetail/$id")
                }
            )
        }
    }
}
