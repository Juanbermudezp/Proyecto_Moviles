package com.jmgg.habitus.ui.premium.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun RoutineCard(
    title: String,
    description: String,
    onSelect: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFF475569))
            .padding(16.dp)
            .clickable { onSelect() }
    ) {
        Text(text = title,
            style = MaterialTheme.typography.titleMedium.copy(color = Color(0xFFF8FAFC))
        )
        Spacer(modifier = Modifier
            .height(4.dp)
        )
        Text(text = description,
            style = MaterialTheme.typography.bodyMedium.copy(color = Color(0xFFF8FAFC))
        )
    }
}

@Preview
@Composable
fun RoutineCardPreview() {
    RoutineCard(
        title = "Rutina ",
        description = "descripcion",
        onSelect = {}
    )
}
