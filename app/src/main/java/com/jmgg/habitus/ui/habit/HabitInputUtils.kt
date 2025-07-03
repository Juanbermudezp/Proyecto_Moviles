package com.jmgg.habitus.ui.habit

import android.app.TimePickerDialog
import android.widget.TimePicker
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext

import androidx.compose.foundation.layout.* // Para Column, Spacer, fillMaxWidth, padding, etc.

import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField

import java.util.Calendar

@Composable
fun FrequencyPicker(
    currentFrequency: String,
    onFrequencySelected: (String) -> Unit
) {
    var showDialog by remember { mutableStateOf(false) }

    Column {
        OutlinedTextField(
            value = currentFrequency,
            onValueChange = {},
            label = { Text("Frecuencia (ej: 3 veces/semana)") },
            modifier = androidx.compose.ui.Modifier.fillMaxWidth(),
            readOnly = true,
            trailingIcon = {
                IconButton(onClick = { showDialog = true }) {
                    Icon(Icons.Default.DateRange, contentDescription = "Elegir frecuencia")
                }
            }
        )

        if (showDialog) {
            AlertDialog(
                onDismissRequest = { showDialog = false },
                title = { Text("Seleccionar frecuencia") },
                text = {
                    Column {
                        listOf("1 vez/semana", "2 veces/semana", "3 veces/semana", "4 veces/semana", "Diario")
                            .forEach { option ->
                                TextButton(onClick = {
                                    onFrequencySelected(option)
                                    showDialog = false
                                }) {
                                    Text(option)
                                }
                            }
                    }
                },
                confirmButton = {},
                dismissButton = {}
            )
        }
    }
}



@Composable
fun ReminderTimePicker(
    currentTime: String,
    onTimeSelected: (String) -> Unit
) {
    val context = LocalContext.current
    val calendar = Calendar.getInstance()

    val hour = calendar.get(Calendar.HOUR_OF_DAY)
    val minute = calendar.get(Calendar.MINUTE)

    OutlinedTextField(
        value = currentTime,
        onValueChange = {},
        label = { Text("Hora de recordatorio") },
        modifier = androidx.compose.ui.Modifier.fillMaxWidth(),
        readOnly = true,
        trailingIcon = {
            IconButton(onClick = {
                TimePickerDialog(
                    context,
                    { _: TimePicker, selectedHour: Int, selectedMinute: Int ->
                        val formattedTime = String.format("%02d:%02d", selectedHour, selectedMinute)
                        onTimeSelected(formattedTime)
                    },
                    hour,
                    minute,
                    true
                ).show()
            }) {
                Icon(Icons.Default.AccessTime, contentDescription = "Elegir hora")
            }
        }
    )
}
