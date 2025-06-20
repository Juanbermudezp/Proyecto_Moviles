package com.jmgg.habitus.ui.premium

import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.jmgg.habitus.ui.premium.components.RoutineCard
import kotlinx.coroutines.launch

@Composable
fun RecommendedRoutinesScreen() {
    val routines = listOf(
        Pair("Dormir Mejor", "Rutina para descansar mejor: leer antes de dormir, evitar pantallas, dormir 8h."),
        Pair("Salud Mental", "Ejercicios de respiración, journaling diario, caminatas relajantes."),
        Pair("Vida Activa", "Ejercicio 30 min diarios, estiramientos matutinos, buena hidratación.")
    )

    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Top
    ) {
        Text(
            text = "Rutinas Recomendadas",
            style = MaterialTheme.typography.headlineSmall
        )

        Spacer(modifier = Modifier.height(16.dp))

        routines.forEach { routine ->
            RoutineCard(
                title = routine.first,
                description = routine.second,
                onSelect = {
                    scope.launch {
                        snackbarHostState.showSnackbar("Rutina '${routine.first}' seleccionada (simulado)")
                    }
                }
            )
            Spacer(modifier = Modifier.height(12.dp))
        }
    }
}
