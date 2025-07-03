package com.jmgg.habitus.ui.premium

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Alignment
import com.jmgg.habitus.HabitusApp
import com.jmgg.habitus.models.Habit
import kotlinx.coroutines.launch

@Composable
fun RoutineDetailScreen(
    routineId: String,
    onRoutineAdded: () -> Unit
) {
    val currentUser = HabitusApp.authViewModel.currentUser.collectAsState().value
    val habitViewModel = HabitusApp.habitViewModel
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    val scrollState = rememberScrollState()


    val routineData = remember(currentUser) {
        getPredefinedRoutine(routineId, currentUser?.id ?: -1)
    }

    if (currentUser == null || routineData == null) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text("Error al cargar rutina.")
        }
        return
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp)
                .padding(padding)
                .verticalScroll(scrollState),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = routineData.title,
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold
            )

            Text("Beneficios:", style = MaterialTheme.typography.titleMedium)
            routineData.benefits.forEach { benefit ->
                Text("• $benefit")
            }

            Spacer(modifier = Modifier.height(12.dp))

            Text("Hábitos sugeridos:", style = MaterialTheme.typography.titleMedium)
            routineData.habits.forEach {
                Text("- ${it.name}")
            }

            Spacer(modifier = Modifier.height(32.dp))

            Button(
                onClick = {
                    scope.launch {
                        routineData.habits.forEach {
                            habitViewModel.addHabit(it.copy(userId = currentUser.id))
                        }
                        snackbarHostState.showSnackbar("Rutina agregada correctamente.")
                        onRoutineAdded()
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Empezar esta rutina")
            }
        }
    }
}
