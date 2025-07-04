package com.jmgg.habitus.data.datasources.remote

import com.jmgg.habitus.data.datasources.HabitDataSource
import com.jmgg.habitus.models.Habit
import com.jmgg.habitus.network.SupabaseApiService

class SupabaseHabitDataSource(
    private val api: SupabaseApiService,
    private val apiKey: String

) : HabitDataSource {

    override suspend fun getHabitsByUser(userId: Int): List<Habit> {
        return api.getHabits(
            authHeader = "Bearer $apiKey",
            apiKey = apiKey,
            userId = "eq.$userId"
        )
    }

    override suspend fun addHabit(habit: Habit) {
        api.insertHabit(
            habit = habit,
            authHeader = "Bearer $apiKey",
            apiKey = apiKey
        )
    }

    override suspend fun deleteHabit(habitId: Int) {
        val response = api.deleteHabit(
            habitId = "eq.$habitId",
            authHeader = "Bearer $apiKey",
            apiKey = apiKey
        )

        if (!response.isSuccessful){
            throw Exception("Error al eliminar habito: ${response.code()}")
        }
    }

    override suspend fun updateHabit(habit: Habit) {
        val response = api.updateHabit(
            habitId = "eq.${habit.id}",
            habit = habit,
            authHeader = "Bearer $apiKey",
            apiKey = apiKey
        )

        if (!response.isSuccessful) {
            throw Exception("Error al actualizar hábito: ${response.code()}")
        }
    }

    override suspend fun getHabitById(habitId: Int): Habit? {
        return api.getHabitById(
            habitId = "eq.$habitId",
            authHeader = "Bearer $apiKey",
            apiKey = apiKey
        ).firstOrNull()
    }
}
