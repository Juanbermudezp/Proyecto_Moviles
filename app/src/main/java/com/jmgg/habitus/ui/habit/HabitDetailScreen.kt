package com.jmgg.habitus.ui.habit

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.jmgg.habitus.HabitusApp

@Composable
fun HabitDetailsScreen(
    habitId: Int,
    onHabitUpdated: () -> Unit
) {
    val viewModel = HabitusApp.habitViewModel
    val habit = viewModel.selectedHabit.collectAsState().value

    var name by remember { mutableStateOf("") }
    var category by remember { mutableStateOf("") }
    var frequency by remember { mutableStateOf("") }
    var reminderTime by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var notes by remember { mutableStateOf("") }
    val scrollState = rememberScrollState()


    // Cargar hábito desde ViewModel
    LaunchedEffect(habitId) {
        viewModel.loadHabitById(habitId)
    }

    // Una vez que el hábito está cargado, llenamos los campos
    LaunchedEffect(habit) {
        habit?.let {
            name = it.name
            category = it.category
            frequency = it.frequency
            reminderTime = it.reminderTime.orEmpty()
            description = it.description
            notes = it.notes
        }
    }

    habit?.let {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp)
                .verticalScroll(scrollState),
            verticalArrangement = Arrangement.Top
        ) {
            Text("Detalle del Hábito", style = MaterialTheme.typography.headlineSmall)
            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(value = name, onValueChange = { name = it }, label = { Text("Nombre") }, modifier = Modifier.fillMaxWidth())
            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(value = category, onValueChange = { category = it }, label = { Text("Categoría") }, modifier = Modifier.fillMaxWidth())
            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(value = frequency, onValueChange = { frequency = it }, label = { Text("Frecuencia") }, modifier = Modifier.fillMaxWidth())
            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(value = reminderTime, onValueChange = { reminderTime = it }, label = { Text("Recordatorio") }, modifier = Modifier.fillMaxWidth())
            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(value = description, onValueChange = { description = it }, label = { Text("Descripción") }, modifier = Modifier.fillMaxWidth())
            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(value = notes, onValueChange = { notes = it }, label = { Text("Notas") }, modifier = Modifier.fillMaxWidth())
            Spacer(modifier = Modifier.height(24.dp))

            Button(onClick = {
                val updated = it.copy(
                    name = name,
                    category = category,
                    frequency = frequency,
                    reminderTime = reminderTime.ifBlank { null },
                    description = description,
                    notes = notes
                )
                viewModel.updateHabit(updated)
                onHabitUpdated()
            }, modifier = Modifier.fillMaxWidth()) {
                Text("Guardar cambios")
            }
        }
    } ?: run {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = androidx.compose.ui.Alignment.Center) {
            Text("Cargando hábito...")
        }
    }
}
