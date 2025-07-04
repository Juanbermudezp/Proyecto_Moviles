package com.jmgg.habitus.ui.stats

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jmgg.habitus.HabitusApp

@Composable
fun StatsScreen() {
    val habitViewModel = HabitusApp.habitViewModel
    val authViewModel = HabitusApp.authViewModel
    val currentUser = authViewModel.currentUser.collectAsState().value
    val scrollState = rememberScrollState()


    val habits by habitViewModel.habits.collectAsState()
    val completedMap by habitViewModel.completedHabits.collectAsState()

    LaunchedEffect(currentUser) {
        currentUser?.let {
            habitViewModel.loadHabits(it.id)
        }
    }

    if (currentUser == null) {
        Box(modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF0F172A)),
            contentAlignment = Alignment.Center
        ) {
            Text(
                "Inicia sesión para ver tus estadísticas",
                color = Color(0xFFF8FAFC)
            )
        }
        return
    }

    val totalHabits = habits.size
    val completed = habits.count { habit ->
        val id = habit.id
        id != null && completedMap[id] == true
    }

    val pending = totalHabits - completed
    val withReminder = habits.count { !it.reminderTime.isNullOrBlank() }
    val byCategory = habits.groupingBy { it.category }.eachCount()
    val topCategories = byCategory.entries.sortedByDescending { it.value }.take(3)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF0F172A))
            .padding(24.dp)
            .verticalScroll(scrollState),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            "Tus estadísticas",
            style = MaterialTheme.typography.headlineSmall.copy(color = Color(0xFFF8FAFC))
        )

        Text(
            text = "Estadísticas del Mes",
            fontSize = 20.sp,
            style = MaterialTheme.typography.headlineSmall.copy(color = Color(0xFFF8FAFC))
        )

        CompletionChart(completed, pending)

        CardStat("Total de hábitos", "$totalHabits")
        CardStat("Con recordatorio", "$withReminder")
        CardStat(
            "Categorías principales",
            topCategories.joinToString { "${it.key} (${it.value})" }
        )

        Spacer(modifier = Modifier.height(12.dp))

        Text("Resumen por categoría:",
            style = MaterialTheme.typography.titleMedium.copy(color = Color(0xFFF8FAFC))
        )
        byCategory.forEach { (category, count) ->
            Text("- $category: $count hábitos")
        }

        Spacer(modifier = Modifier.height(8.dp))

        Text(text = "Días activos:",
        fontSize = 18.sp,
        style = MaterialTheme.typography.titleMedium.copy(color = Color(0xFFF8FAFC))
        )
    }
}

@Composable
fun CardStat(title: String, value: String) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF475569)),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = title,
                style = MaterialTheme.typography.titleMedium.copy(color = Color(0xFFF8FAFC))
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = value,
                style = MaterialTheme.typography.headlineSmall.copy(color = Color(0xFFF8FAFC))
            )
        }
    }
}

@Composable
fun CompletionChart(completed: Int, pending: Int) {
    val total = completed + pending

    if (total == 0) {
        Text(
            "No hay datos para mostrar el progreso aún.",
            color = Color(0xFFF8FAFC)
        )
        return
    }

    Column {
        Text(
            "Progreso de hábitos",
            style = MaterialTheme.typography.titleMedium.copy(color = Color(0xFFF8FAFC))
        )

        Spacer(Modifier.height(8.dp))

        Row(Modifier.fillMaxWidth()) {
            if (completed > 0) {
                Box(
                    Modifier
                        .weight(completed.toFloat() / total)
                        .height(24.dp)
                        .background(Color(0xFF4CAF50))
                )
            }
            if (pending > 0) {
                Box(
                    Modifier
                        .weight(pending.toFloat() / total)
                        .height(24.dp)
                        .background(Color(0xFFF44336))
                )
            }
        }

        Spacer(Modifier.height(8.dp))

        Text(
            "✅ Completados: $completed   ❌ Pendientes: $pending",
            color = Color(0xFFF8FAFC),
            fontSize = 14.sp
        )
    }
}
