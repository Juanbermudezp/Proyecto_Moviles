package com.jmgg.habitus.data.repositories

import com.jmgg.habitus.data.datasources.HabitDataSource
import com.jmgg.habitus.models.Habit

class HabitRepository(private val dataSource: HabitDataSource) {

    suspend fun getHabitsByUser(userId: Int): List<Habit> {
        return dataSource.getHabitsByUser(userId)
    }

    suspend fun addHabit(habit: Habit) {
        dataSource.addHabit(habit)
    }

    suspend fun deleteHabit(habitId: Int, userId: Int) {
        dataSource.deleteHabit(habitId)
    }

    suspend fun updateHabit(habit: Habit) {
        dataSource.updateHabit(habit)
    }

    suspend fun getHabitById(habitId: Int): Habit? {
        return dataSource.getHabitById(habitId)
    }
}
