package com.jmgg.habitus.ui.stats

import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun StatsScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Top
    ) {
        Text(
            text = "Estadísticas del Mes",
            style = MaterialTheme.typography.headlineSmall
        )

        Spacer(modifier = Modifier.height(24.dp))

        StatItem(label = "Hábitos completados", value = "85%")
        StatItem(label = "Tomar agua", value = "50%")
        StatItem(label = "Hacer ejercicio", value = "25%")

        Spacer(modifier = Modifier.height(24.dp))

        Text(text = "Días activos:", style = MaterialTheme.typography.titleMedium)
        Spacer(modifier = Modifier.height(8.dp))

        Text("✔️ Lunes\n✔️ Martes\n✔️ Miércoles\n✔️ Jueves\n✔️ Viernes")
    }
}

@Composable
fun StatItem(label: String, value: String) {
    Column(modifier = Modifier.padding(bottom = 16.dp)) {
        Text(text = label, style = MaterialTheme.typography.bodyLarge)
        Text(text = value, style = MaterialTheme.typography.titleLarge)
    }
}
