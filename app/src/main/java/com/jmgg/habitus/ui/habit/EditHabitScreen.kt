package com.jmgg.habitus.ui.habit

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.platform.LocalContext
import com.jmgg.habitus.HabitusApp
import com.jmgg.habitus.utils.AlarmScheduler
import kotlinx.coroutines.launch

@Composable
fun EditHabitScreen(
    habitId: Int,
    onHabitUpdated: () -> Unit
) {
    val habitViewModel = HabitusApp.habitViewModel
    val scope = rememberCoroutineScope()
    val context = LocalContext.current // ✅ Mover aquí para usarlo correctamente luego

    val habitState by habitViewModel.selectedHabit.collectAsState()

    var name by remember { mutableStateOf("") }
    var category by remember { mutableStateOf("") }
    var frequency by remember { mutableStateOf("") }
    var reminderTime by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var notes by remember { mutableStateOf("") }
    val scrollState = rememberScrollState()

    LaunchedEffect(habitId) {
        habitViewModel.loadHabitById(habitId)
    }

    LaunchedEffect(habitState) {
        habitState?.let {
            name = it.name
            category = it.category
            frequency = it.frequency
            reminderTime = it.reminderTime ?: ""
            description = it.description
            notes = it.notes
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF0F172A))
            .padding(24.dp)
            .verticalScroll(scrollState),
        verticalArrangement = Arrangement.Top
    ) {
        val labelColor = Color(0xFFF8FAFC)

        Text(
            "Editar Hábito",
            style = MaterialTheme.typography.headlineSmall.copy(color = Color(0xFFF8FAFC))
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Nombre del hábito", color = labelColor) },
            textStyle = MaterialTheme.typography.bodyLarge.copy(color = labelColor),
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next)
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = category,
            onValueChange = { category = it },
            label = { Text("Categoría", color = labelColor) },
            textStyle = MaterialTheme.typography.bodyLarge.copy(color = labelColor),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        FrequencyPicker(
            currentFrequency = frequency,
            onFrequencySelected = { frequency = it }
        )

        Spacer(modifier = Modifier.height(8.dp))

        ReminderTimePicker(
            currentTime = reminderTime,
            onTimeSelected = { reminderTime = it }
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = description,
            onValueChange = { description = it },
            label = { Text("Descripción", color = labelColor) },
            textStyle = MaterialTheme.typography.bodyLarge.copy(color = labelColor),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = notes,
            onValueChange = { notes = it },
            label = { Text("Notas adicionales", color = labelColor) },
            textStyle = MaterialTheme.typography.bodyLarge.copy(color = labelColor),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {
                habitState?.let { original ->
                    val updatedHabit = original.copy(
                        name = name,
                        category = category,
                        frequency = frequency,
                        reminderTime = reminderTime.takeIf { it.isNotBlank() },
                        description = description,
                        notes = notes
                    )
                    habitViewModel.updateHabit(updatedHabit)

                    // 👉 Notificación de recordatorio si se indicó hora
                    if (reminderTime.isNotBlank()) {
                        val (hour, minute) = reminderTime.split(":").map { it.toInt() }
                        AlarmScheduler.scheduleHabitReminder(
                            context = context,
                            hour = hour,
                            minute = minute,
                            habitName = name
                        )
                    }

                    onHabitUpdated()
                }
            },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF34D399),
                contentColor = Color(0xFF0F172A)
            )
        ) {
            Text("Guardar cambios")
        }
    }
}
