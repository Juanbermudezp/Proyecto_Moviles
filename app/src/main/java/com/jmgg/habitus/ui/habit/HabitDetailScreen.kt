package com.jmgg.habitus.ui.habit

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Button
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
                .background(Color(0xFF0F172A))
                .padding(24.dp)
                .verticalScroll(scrollState),
            verticalArrangement = Arrangement.Top
        ) {
            val fieldColors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Color(0xFF6366F1),
                unfocusedBorderColor = Color(0xFFF8FAFC),
                textColor = Color(0xFFF8FAFC),
                cursorColor = Color(0xFF6366F1)
            )
            val labelColor = Color(0xFFF8FAFC)

            Text(
                "Detalle del Hábito",
                style = MaterialTheme.typography.headlineSmall.copy(color = Color(0xFFF8FAFC))
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(value = name, onValueChange = { name = it }, label = {
                Text("Nombre", color = labelColor)
            }, modifier = Modifier.fillMaxWidth(), colors = fieldColors)

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(value = category, onValueChange = { category = it }, label = {
                Text("Categoría", color = labelColor)
            }, modifier = Modifier.fillMaxWidth(), colors = fieldColors)

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(value = frequency, onValueChange = { frequency = it }, label = {
                Text("Frecuencia", color = labelColor)
            }, modifier = Modifier.fillMaxWidth(), colors = fieldColors)

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(value = reminderTime, onValueChange = { reminderTime = it }, label = {
                Text("Recordatorio", color = labelColor)
            }, modifier = Modifier.fillMaxWidth(), colors = fieldColors)

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(value = description, onValueChange = { description = it }, label = {
                Text("Descripción", color = labelColor)
            }, modifier = Modifier.fillMaxWidth(), colors = fieldColors)

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(value = notes, onValueChange = { notes = it }, label = {
                Text("Notas", color = labelColor)
            }, modifier = Modifier.fillMaxWidth(), colors = fieldColors)

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
            }, modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color(0xFF34D399),
                    contentColor = Color(0xFF0F172A)
            )) {
                Text("Guardar cambios")
            }
        }
    } ?: run {
        Box(
            modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF0F172A)),
            contentAlignment = androidx.compose.ui.Alignment.Center
        ) {
            Text(
                "Cargando hábito...",
                color = Color(0xFFF8FAFC)
            )
        }
    }
}
