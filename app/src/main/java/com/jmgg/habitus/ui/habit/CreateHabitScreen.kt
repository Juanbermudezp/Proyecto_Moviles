package com.jmgg.habitus.ui.habit

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.jmgg.habitus.HabitusApp
import com.jmgg.habitus.models.Habit
import com.jmgg.habitus.utils.AlarmScheduler
import kotlinx.coroutines.launch

@Composable
fun CreateHabitScreen(
    onHabitCreated: () -> Unit,
    habitId: Int? = null
) {
    val habitViewModel = HabitusApp.habitViewModel
    val user = HabitusApp.authViewModel.currentUser.collectAsState().value
    val scrollState = rememberScrollState()
    val coroutineScope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }
    val context = LocalContext.current

    var name by remember { mutableStateOf("") }
    var category by remember { mutableStateOf("") }
    var frequency by remember { mutableStateOf("") }
    var reminderTime by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var notes by remember { mutableStateOf("") }

    LaunchedEffect(habitId) {
        if (habitId != null) {
            habitViewModel.loadHabitById(habitId)
        }
    }

    val selectedHabit = habitViewModel.selectedHabit.collectAsState().value

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
        containerColor = Color(0xFF0F172A),
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFF0F172A))
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
                label = { Text("Nombre del hábito",
                    color = Color(0xFFF8FAFC)) },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                textStyle = MaterialTheme.typography.bodyLarge.copy(color = Color(0xFFF8FAFC))
            )

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = category,
                onValueChange = { category = it },
                label = { Text("Categoría",
                    color = Color(0xFFF8FAFC)) },
                modifier = Modifier.fillMaxWidth(),
                textStyle = MaterialTheme.typography.bodyLarge.copy(color = Color(0xFFF8FAFC))
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
                label = { Text("Descripción",
                    color = Color(0xFFF8FAFC))},
                modifier = Modifier.fillMaxWidth(),
                textStyle = MaterialTheme.typography.bodyLarge.copy(color = Color(0xFFF8FAFC))
            )

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = notes,
                onValueChange = { notes = it },
                label = { Text("Notas adicionales",
                    color = Color(0xFFF8FAFC)) },
                modifier = Modifier.fillMaxWidth(),
                textStyle = MaterialTheme.typography.bodyLarge.copy(color = Color(0xFFF8FAFC))
            )

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = {
                    if (user != null && name.isNotBlank()) {
                        val habit = Habit(
                            id = habitId,
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

                                if (reminderTime.isNotBlank()) {
                                    val (hour, minute) = reminderTime.split(":").map { it.toInt() }
                                    AlarmScheduler.scheduleHabitReminder(
                                        context = context,
                                        hour = hour,
                                        minute = minute,
                                        habitName = name
                                    )
                                }

                                snackbarHostState.showSnackbar("Hábito guardado")
                                onHabitCreated()
                            } catch (e: Exception) {
                                snackbarHostState.showSnackbar("Error: ${e.message}")
                                Log.e("CreateHabit", "Error al guardar hábito", e)
                            }
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF34D399),
                    contentColor = Color(0xFF0F172A)
                )
            ) {
                Text("Guardar hábito")
            }
        }
    }
}
