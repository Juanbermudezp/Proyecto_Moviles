package com.jmgg.habitus.ui.main

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.jmgg.habitus.HabitusApp
import com.jmgg.habitus.ui.main.components.HabitCard

@Composable
fun MainScreen(navController: NavController) {
    val habitViewModel = HabitusApp.habitViewModel
    val currentUser = HabitusApp.authViewModel.currentUser.collectAsState().value
    val habits by habitViewModel.habits.collectAsState()

    LaunchedEffect(currentUser) {
        currentUser?.let {
            habitViewModel.loadHabits(it.id)
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        if (habits.isEmpty()) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("Aún no tienes hábitos registrados")
            }
        } else {
            LazyColumn(modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)) {
                items(habits) { habit ->
                    HabitCard(
                        habit = habit,
                        isPremium = currentUser?.isPremium == true,
                        onClick = {
                            navController.navigate("createHabit/${habit.id}")
                        },
                        onEdit = {
                            navController.navigate("editHabit/${habit.id}")
                        },
                        onDelete = {
                            if (habit.id != null && habit.userId != null) {
                                habitViewModel.deleteHabit(habit.id, habit.userId)
                            }
                        }
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }

    }
}
