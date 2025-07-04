package com.jmgg.habitus.ui.premium

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
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
        Box(modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF0F172A)),
            contentAlignment = Alignment.Center
        ) {
            Text(
                "Error al cargar rutina.",
                color = Color(0xFFF8FAFC)
            )
        }
        return
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        containerColor = Color(0xFF0F172A)
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFF0F172A))
                .padding(24.dp)
                .padding(padding)
                .verticalScroll(scrollState),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = routineData.title,
                style = MaterialTheme.typography.headlineSmall.copy(color = Color(0xFFF8FAFC)),
                fontWeight = FontWeight.Bold
            )

            Text(
                text = routineData.description,
                style = MaterialTheme.typography.bodyLarge.copy(color = Color(0xFFF8FAFC))
            )

            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    "Beneficios:",
                    style = MaterialTheme.typography.titleMedium.copy(color = Color(0xFFF8FAFC))
                )
                routineData.benefits.forEach { benefit ->
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text("•", color = Color(0xFF34D399))
                        Spacer(Modifier.width(8.dp))
                        Text(benefit, color = Color(0xFFF8FAFC))
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            Column(
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text(
                    "Hábitos sugeridos:",
                    style = MaterialTheme.typography.titleMedium.copy(color = Color(0xFFF8FAFC))
                )

                routineData.habits.forEach {
                    Button(
                        onClick = {},
                        enabled = false,
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF475569),
                            contentColor = Color(0xFFF8FAFC),
                            disabledContainerColor = Color(0xFF475569),
                            disabledContentColor = Color(0xFFF8FAFC)
                        )
                    ) {
                        Text(it.name)
                    }
                }
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
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF34D399),
                    contentColor = Color(0xFF0F172A)
                )
            ) {
                Text("Empezar esta rutina")
            }
        }
    }
}
