package com.jmgg.habitus.ui.stats

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.jmgg.habitus.HabitusApp
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@Composable
fun StatsScreen() {
    val habitViewModel = HabitusApp.habitViewModel
    val authViewModel = HabitusApp.authViewModel
    val currentUser = authViewModel.currentUser.collectAsState().value

    val habits by habitViewModel.habits.collectAsState()
    val scope = rememberCoroutineScope()

    LaunchedEffect(currentUser) {
        currentUser?.let {
            habitViewModel.loadHabits(it.id)
        }
    }

    if (currentUser == null) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text("Inicia sesión para ver tus estadísticas")
        }
        return
    }

    val totalHabits = habits.size
    val withReminder = habits.count { !it.reminderTime.isNullOrBlank() }
    val byCategory = habits.groupingBy { it.category }.eachCount()
    val topCategories = byCategory.entries.sortedByDescending { it.value }.take(3)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text("Tus estadísticas", style = MaterialTheme.typography.headlineSmall)

        CardStat("Total de hábitos", "$totalHabits")
        CardStat("Con recordatorio", "$withReminder")
        CardStat("Categorías principales", topCategories.joinToString { "${it.key} (${it.value})" })

        Spacer(modifier = Modifier.height(12.dp))

        Text("Resumen por categoría:", style = MaterialTheme.typography.titleMedium)
        byCategory.forEach { (category, count) ->
            Text("- $category: $count hábitos")
        }
    }
}

@Composable
fun CardStat(title: String, value: String) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = title, style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = value, style = MaterialTheme.typography.headlineSmall)
        }
    }
}
