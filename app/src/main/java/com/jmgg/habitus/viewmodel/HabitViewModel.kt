package com.jmgg.habitus.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jmgg.habitus.data.repositories.HabitRepository
import com.jmgg.habitus.models.Habit
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class HabitViewModel(private val repository: HabitRepository) : ViewModel() {

    private val _habits = MutableStateFlow<List<Habit>>(emptyList())
    val habits: StateFlow<List<Habit>> = _habits

    private val _selectedHabit = MutableStateFlow<Habit?>(null)
    val selectedHabit: StateFlow<Habit?> = _selectedHabit

    private val _completedHabits = MutableStateFlow<Map<Int, Boolean>>(emptyMap())
    val completedHabits: StateFlow<Map<Int, Boolean>> = _completedHabits

    fun toggleHabitCompleted(habitId: Int) {
        _completedHabits.value = _completedHabits.value.toMutableMap().apply {
            this[habitId] = !(this[habitId] ?: false)
        }
    }


    fun loadHabits(userId: Int) {
        viewModelScope.launch {
            _habits.value = repository.getHabitsByUser(userId)
        }
    }

    fun addHabit(habit: Habit) {
        viewModelScope.launch {
            repository.addHabit(habit)
            loadHabits(habit.userId)
        }
    }

    fun deleteHabit(habitId: Int, userId: Int) {
        viewModelScope.launch {
            repository.deleteHabit(habitId, userId)
            loadHabits(userId)
        }
    }

    fun updateHabit(habit: Habit) {
        viewModelScope.launch {
            repository.updateHabit(habit)
            loadHabits(habit.userId)
        }
    }

    fun loadHabitById(habitId: Int) {
        viewModelScope.launch {
            _selectedHabit.value = repository.getHabitById(habitId)
        }
    }
}
