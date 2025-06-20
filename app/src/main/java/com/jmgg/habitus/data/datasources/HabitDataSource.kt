package com.jmgg.habitus.data.datasources

import com.jmgg.habitus.models.Habit

interface HabitDataSource{
    suspend fun getHabitsByUser(userId: Int): List<Habit>
    suspend fun addHabit(habit: Habit)
    suspend fun deleteHabit(habitId: Int)
    suspend fun updateHabit(habit: Habit)
    suspend fun getHabitById(habitId: Int): Habit?
}