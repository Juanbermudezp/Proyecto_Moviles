package com.jmgg.habitus.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.jmgg.habitus.ui.auth.LoginScreen
import com.jmgg.habitus.ui.auth.RegisterScreen
import com.jmgg.habitus.ui.habit.CreateHabitScreen
import com.jmgg.habitus.ui.habit.EditHabitScreen
import com.jmgg.habitus.ui.habit.HabitDetailsScreen
import com.jmgg.habitus.ui.main.MainScreen
import com.jmgg.habitus.ui.premium.RecommendedRoutinesScreen
import com.jmgg.habitus.ui.stats.StatsScreen

@Composable
fun AppNavHost(navController: NavHostController, startDestination: String = "login") {
    NavHost(navController = navController, startDestination = startDestination) {

        composable("login") {
            LoginScreen(
                onLoginSuccess = { navController.navigate("main") },
                onNavigateToRegister = { navController.navigate("register") }
            )
        }

        composable("register") {
            RegisterScreen(
                onRegistrationSuccess = { navController.navigate("main") },
                onNavigateToLogin = { navController.popBackStack() }
            )
        }

        composable("createHabit") {
            CreateHabitScreen(
                onHabitCreated = {
                    navController.popBackStack()
                }
            )
        }

        composable("habitDetails/{habitId}") { backStackEntry ->
            val habitId = backStackEntry.arguments?.getString("habitId")?.toIntOrNull() ?: return@composable
            HabitDetailsScreen(
                habitId = habitId,
                onHabitUpdated = {
                    navController.popBackStack()
                }
            )
        }

        composable("editHabit/{habitId}") { backStackEntry ->
            val habitId = backStackEntry.arguments?.getString("habitId")?.toIntOrNull() ?: return@composable
            EditHabitScreen(
                habitId = habitId,
                onHabitUpdated = {
                    navController.popBackStack()
                }
            )
        }


        composable("stats") {
            StatsScreen()
        }

        composable("routines") {
            RecommendedRoutinesScreen()
        }

        composable("createHabit/{habitId}", arguments = listOf(
            navArgument("habitId") { type = NavType.IntType; defaultValue = -1 }
        )) { backStackEntry ->
            val habitId = backStackEntry.arguments?.getInt("habitId")?.takeIf { it != -1 }
            CreateHabitScreen(onHabitCreated = { navController.popBackStack() }, habitId = habitId)
        }


        composable("main") {
            MainScreen(navController)
        }
    }
}