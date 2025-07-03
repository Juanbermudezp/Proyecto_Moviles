package com.jmgg.habitus.ui.habit

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.text.input.ImeAction
import com.jmgg.habitus.HabitusApp
import com.jmgg.habitus.models.Habit
import kotlinx.coroutines.launch

@Composable
fun CreateHabitScreen(
    onHabitCreated: () -> Unit,
    habitId: Int? = null //para la navegacion premium
) {
    val habitViewModel = HabitusApp.habitViewModel
    val user = HabitusApp.authViewModel.currentUser.collectAsState().value
    val scrollState = rememberScrollState()


    val coroutineScope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    var name by remember { mutableStateOf("") }
    var category by remember { mutableStateOf("") }
    var frequency by remember { mutableStateOf("") }
    var reminderTime by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var notes by remember { mutableStateOf("") }

    // ✅ Cargar hábito si es edición
    LaunchedEffect(habitId) {
        if (habitId != null) {
            habitViewModel.loadHabitById(habitId)
        }
    }

    val selectedHabit = habitViewModel.selectedHabit.collectAsState().value

    // ✅ Precargar campos si existe
    LaunchedEffect(selectedHabit) {
        selectedHabit?.let {
            name = it.name
            category = it.category
            frequency = it.frequency
            reminderTime = it.reminderTime ?: ""
            description = it.description
            notes = it.notes
        }
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
            verticalArrangement = Arrangement.Top
        ) {
            Text("Nuevo Hábito", style = MaterialTheme.typography.headlineSmall)
            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("Nombre del hábito") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next)
            )

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = category,
                onValueChange = { category = it },
                label = { Text("Categoría") },
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
                label = { Text("Descripción") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = notes,
                onValueChange = { notes = it },
                label = { Text("Notas adicionales") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = {
                    if (user != null && name.isNotBlank()) {
                        val habit = Habit(
                            id = habitId, // ✅ Si es edición, usar el ID existente
                            userId = user.id,
                            name = name,
                            category = category,
                            frequency = frequency,
                            reminderTime = reminderTime.takeIf { it.isNotBlank() },
                            description = description,
                            notes = notes
                        )
                        coroutineScope.launch {
                            try {
                                if (habitId != null) {
                                    habitViewModel.updateHabit(habit)
                                } else {
                                    habitViewModel.addHabit(habit)
                                }
                               // habitViewModel.addHabit(habit)
                                snackbarHostState.showSnackbar("Hábito guardado")
                                onHabitCreated()
                            } catch (e: Exception) {
                                snackbarHostState.showSnackbar("Error: ${e.message}")
                                Log.e("CreateHabit", "Error al guardar hábito", e)
                            }
                        }

                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Guardar hábito")
            }
        }
    }
}
