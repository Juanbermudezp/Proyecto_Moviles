package com.jmgg.habitus.ui.premium

import com.jmgg.habitus.models.Habit

data class RoutineDefinition(
    val title: String,
    val description: String,
    val benefits: List<String>,
    val habits: List<Habit>
)

fun getPredefinedRoutine(id: String, userId: Int): RoutineDefinition? {
    return when (id) {
        "sleep" -> RoutineDefinition(
            title = "Dormir mejor y más rápido",
            description = "Esta rutina te ayudará a mejorar la calidad de tu sueño y a establecer un horario saludable.",
            benefits = listOf("Dormir más rápido", "Menos estrés", "Horario saludable", "Más energía en el día"),
            habits = listOf(
                Habit(userId = userId, name = "Dormir 8 horas", category = "Salud", frequency = "Diario"),
                Habit(userId = userId, name = "Leer antes de dormir", category = "Salud", frequency = "Diario"),
                Habit(userId = userId, name = "Evitar pantallas", category = "Salud", frequency = "Diario")
            )
        )

        "mental" -> RoutineDefinition(
            title = "Salud mental y relajación",
            description = "Esta rutina está diseñada para mejorar tu bienestar mental y ayudarte a relajarte.",
            benefits = listOf("Reducir ansiedad", "Mejor enfoque", "Mayor autocontrol"),
            habits = listOf(
                Habit(userId = userId, name = "Respirar profundo", category = "Mental", frequency = "Diario"),
                Habit(userId = userId, name = "Journaling", category = "Mental", frequency = "Diario"),
                Habit(userId = userId, name = "Paseo al aire libre", category = "Mental", frequency = "3 veces/semana")
            )
        )

        "fitness" -> RoutineDefinition(
            title = "Estilo de vida activo",
            description = "Esta rutina te ayudará a mantenerte activo y en forma, mejorando tu salud física y mental.",
            benefits = listOf("Más energía", "Mejor salud", "Mayor fuerza"),
            habits = listOf(
                Habit(userId = userId, name = "Ejercicio diario", category = "Físico", frequency = "Diario"),
                Habit(userId = userId, name = "Beber agua", category = "Físico", frequency = "Diario"),
                Habit(userId = userId, name = "Estiramientos", category = "Físico", frequency = "Diario")
            )
        )

        else -> null
    }
}
