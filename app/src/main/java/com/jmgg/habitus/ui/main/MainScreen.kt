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
import com.jmgg.habitus.ui.main.components.PremiumBottomNavBar

@Composable
fun MainScreen(navController: NavController) {
    val habitViewModel = HabitusApp.habitViewModel
    val authViewModel = HabitusApp.authViewModel
    val currentUser = authViewModel.currentUser.collectAsState().value

    val habits by habitViewModel.habits.collectAsState()

    LaunchedEffect(currentUser) {
        currentUser?.let {
            habitViewModel.loadHabits(it.id)
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Mis Hábitos") },
                actions = {
                    // Aquí podrías agregar botón para cerrar sesión, etc.
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = {
                navController.navigate("createHabit")
            }) {
                Text("+")
            }
        },
        bottomBar = {
            if (currentUser?.isPremium == true) {
                PremiumBottomNavBar(
                    currentRoute = navController.currentBackStackEntry?.destination?.route,
                    onNavigateToStats = { navController.navigate("stats") },
                    onNavigateToRoutines = { navController.navigate("routines") }
                )
            }
        }

    ) { padding ->
        Box(modifier = Modifier
            .fillMaxSize()
            .padding(padding)) {

            if (habits.isEmpty()) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("Aún no tienes hábitos registrados")
                }
            } else {
                LazyColumn {
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
                                if (habit.id != null) {
                                    habitViewModel.deleteHabit(habit.id, habit.userId)
                                }
                            }
                        )
                    }
                }

            }
        }
    }
}
