package com.jmgg.habitus.ui.habit

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.text.input.ImeAction
import com.jmgg.habitus.HabitusApp
import com.jmgg.habitus.models.Habit
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@Composable
fun EditHabitScreen(
    habitId: Int,
    onHabitUpdated: () -> Unit
) {
    val habitViewModel = HabitusApp.habitViewModel
    val scope = rememberCoroutineScope()

    val habitState by habitViewModel.selectedHabit.collectAsState()

    var name by remember { mutableStateOf("") }
    var category by remember { mutableStateOf("") }
    var frequency by remember { mutableStateOf("") }
    var reminderTime by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var notes by remember { mutableStateOf("") }
    val scrollState = rememberScrollState()


    // Cargar hábito solo una vez
    LaunchedEffect(habitId) {
        habitViewModel.loadHabitById(habitId)
    }

    // Cuando el hábito se carga, rellenar los campos
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
        val textFieldColors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = Color(0xFF6366F1),
            unfocusedBorderColor = Color(0xFFF8FAFC),
            textColor = Color(0xFFF8FAFC),
            cursorColor = Color(0xFF6366F1)
        )

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
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
            colors = textFieldColors
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = category,
            onValueChange = { category = it },
            label = { Text("Categoría", color = labelColor) },
            modifier = Modifier.fillMaxWidth(),
            colors = textFieldColors
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
            modifier = Modifier.fillMaxWidth(),
            colors = textFieldColors
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = notes,
            onValueChange = { notes = it },
            label = { Text("Notas adicionales", color = labelColor) },
            modifier = Modifier.fillMaxWidth(),
            colors = textFieldColors
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
                    onHabitUpdated()
                }
            },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = Color(0xFF34D399),
                contentColor = Color(0xFF0F172A)
            )
        ) {
            Text("Guardar cambios")
        }
    }
}