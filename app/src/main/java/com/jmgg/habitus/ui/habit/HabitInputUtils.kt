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
import androidx.compose.foundation.layout.*
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.ui.graphics.Color
import androidx.compose.material3.TextButton
import java.util.Calendar

@Composable
fun FrequencyPicker(
    currentFrequency: String,
    onFrequencySelected: (String) -> Unit
) {
    var showDialog by remember { mutableStateOf(false) }

    val colors = TextFieldDefaults.outlinedTextFieldColors(
        focusedBorderColor = Color(0xFF6366F1),
        unfocusedBorderColor = Color(0xFFF8FAFC),
        textColor = Color(0xFFF8FAFC),
        cursorColor = Color(0xFF6366F1)
    )

    Column {
        OutlinedTextField(
            value = currentFrequency,
            onValueChange = {},
            label = { Text("Frecuencia (ej: 3 veces/semana)", color = Color(0xFFF8FAFC)) },
            modifier = androidx.compose.ui.Modifier.fillMaxWidth(),
            readOnly = true,
            trailingIcon = {
                IconButton(onClick = { showDialog = true }) {
                    Icon(
                        Icons.Default.DateRange,
                        contentDescription = "Elegir frecuencia",
                        tint = Color(0xFF6366F1)
                    )
                }
            },
            colors = colors
        )

        if (showDialog) {
            AlertDialog(
                onDismissRequest = { showDialog = false },
                containerColor = Color(0xFF475569),
                title = { Text("Seleccionar frecuencia", color = Color(0xFFF8FAFC)) },
                text = {
                    Column {
                        listOf("1 vez/semana", "2 veces/semana", "3 veces/semana", "4 veces/semana", "Diario")
                            .forEach { option ->
                                TextButton(onClick = {
                                    onFrequencySelected(option)
                                    showDialog = false
                                }) {
                                    Text(option, color = Color(0xFFF8FAFC))
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

    val colors = TextFieldDefaults.outlinedTextFieldColors(
        focusedBorderColor = Color(0xFF6366F1),
        unfocusedBorderColor = Color(0xFFF8FAFC),
        textColor = Color(0xFFF8FAFC),
        cursorColor = Color(0xFF6366F1)
    )

    OutlinedTextField(
        value = currentTime,
        onValueChange = {},
        label = { Text("Hora de recordatorio", color = Color(0xFFF8FAFC)) },
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
                Icon(
                    Icons.Default.AccessTime,
                    contentDescription = "Elegir hora",
                    tint = Color(0xFF6366F1)
                )
            }
        },
        colors = colors
    )
}
