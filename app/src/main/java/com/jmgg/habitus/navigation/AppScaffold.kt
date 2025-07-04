package com.jmgg.habitus.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.jmgg.habitus.HabitusApp
import com.jmgg.habitus.ui.main.components.MainBottomNavBar
import com.jmgg.habitus.ui.main.components.MainTopBar
import androidx.compose.foundation.layout.Box
import androidx.compose.ui.graphics.Color


@Composable
fun AppScaffold() {
    val navController = rememberNavController()
    val currentUser = HabitusApp.authViewModel.currentUser.collectAsState().value
    val currentRoute = navController.currentBackStackEntryFlow.collectAsState(null).value?.destination?.route

    val hideBars = currentRoute in listOf("login", "register")

    Scaffold(
        containerColor = Color(0xFF0F172A),
        topBar = {
            if (!hideBars) {
                MainTopBar(
                    isPremium = currentUser?.isPremium == true,
                    currentRoute = currentRoute,
                    onNavigateToHome = { navController.navigate("main") },
                    onNavigateToStats = { navController.navigate("stats") },
                    onNavigateToRoutines = { navController.navigate("routines") },
                    onLogout = {
                        HabitusApp.authViewModel.logout()
                        navController.navigate("login") {
                            popUpTo(0)
                            launchSingleTop = true
                        }
                    }
                )
            }

        },
        bottomBar = {
            if (!hideBars) {
                MainBottomNavBar(
                    isPremium = currentUser?.isPremium == true,
                    currentRoute = currentRoute,
                    onNavigateToHome = { navController.navigate("main") },
                    onNavigateToStats = { navController.navigate("stats") },
                    onNavigateToRoutines = { navController.navigate("routines") },
                )
            }
        },
        floatingActionButton = {
            if (currentRoute == "main") {
                FloatingActionButton(
                    containerColor = Color(0xFF34D399),
                    contentColor = Color(0xFF0F172A),
                    onClick = {
                    navController.navigate("createHabit") }
                ) {
                    Text("+")
                }
            }
        }
    ) { padding ->
        Box(modifier = Modifier
            .fillMaxSize()
            .padding(padding)
            .background(Color(0xFF0F172A))
        ) {
            AppNavHost(
                navController = navController,
                modifier = Modifier.padding(padding)
            )
        }
    }
}

