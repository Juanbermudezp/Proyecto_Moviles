package com.jmgg.habitus.ui.main.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Card
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.jmgg.habitus.models.Habit

@Composable
fun HabitCard(
    habit: Habit,
    isPremium: Boolean,
    isCompleted: Boolean,
    onToggleCompleted: () -> Unit,
    onClick: () -> Unit,
    onEdit: () -> Unit,
    onDelete: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(8.dp)
            .background(Color(0xFF475569))
            .then(
                if (isPremium && habit.id != null) Modifier.clickable { onClick() }
                else Modifier
            )
    ) {
        Column(modifier = Modifier
            .padding(16.dp)
        ) {

            Row(verticalAlignment = Alignment.CenterVertically) {
                Checkbox(
                    checked = isCompleted,
                    onCheckedChange = { onToggleCompleted() },
                    colors = CheckboxDefaults.colors(
                        checkedColor = Color(0xFF34D399),
                        uncheckedColor = Color(0xFFF8FAFC),
                        checkmarkColor = Color(0xFF0F172A)
                    )
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(habit.name,
                    style = MaterialTheme.typography.headlineSmall.copy(color = Color(0xFFF8FAFC))
                )
            }

            Text(habit.category,
                style = MaterialTheme.typography.bodyMedium.copy(color = Color(0xFFF8FAFC))
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                IconButton(onClick = onEdit) {
                    Icon(Icons.Default.Edit,
                        contentDescription = "Editar hábito",
                        tint = Color(0xFF6366F1)
                    )
                }
                IconButton(onClick = onDelete) {
                    Icon(Icons.Default.Delete,
                        contentDescription = "Eliminar hábito",
                        tint = Color(0xFF6366F1)
                    )
                }
            }
        }
    }
}