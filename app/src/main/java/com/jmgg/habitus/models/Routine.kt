package com.jmgg.habitus.models

data class Routine(
    val id: Int = 0,
    val title: String,
    val description: String,
    val habits: List<Habit> = emptyList()
)