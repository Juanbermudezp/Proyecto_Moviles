package com.jmgg.habitus.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.jmgg.habitus.Context.markOnboardingComplete
import com.jmgg.habitus.ui.auth.LoginScreen
import com.jmgg.habitus.ui.auth.RegisterScreen
import com.jmgg.habitus.ui.habit.CreateHabitScreen
import com.jmgg.habitus.ui.habit.EditHabitScreen
import com.jmgg.habitus.ui.habit.HabitDetailsScreen
import com.jmgg.habitus.ui.main.MainScreen
import com.jmgg.habitus.ui.premium.RecommendedRoutinesScreen
import com.jmgg.habitus.ui.premium.RoutineDetailScreen
import com.jmgg.habitus.ui.startUp.FeaturesScreens.PremiumVersionOfferScreen
import com.jmgg.habitus.ui.startUp.IntroCarousel
import com.jmgg.habitus.ui.stats.StatsScreen
import kotlinx.coroutines.launch


@Composable
fun AppNavHost(
    navController: NavHostController,
    startDestination: String = "login",
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {

        // 👉 Intro (carousel de bienvenida)
        composable("intro") {
            val context = LocalContext.current
            val scope = rememberCoroutineScope()
            IntroCarousel(onCarouselEnd = {
                scope.launch {
                    markOnboardingComplete(context)
                }
                navController.navigate("main") {
                    popUpTo(0) // Limpia el stack
                }
            })
        }

        // 👉 Login y registro
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

        // 👉 Pantalla principal
        composable("main") {
            MainScreen(navController)
        }

        // 👉 Crear hábito (nueva o edición con ID)
        composable("createHabit") {
            CreateHabitScreen(
                onHabitCreated = { navController.popBackStack() }
            )
        }

        composable(
            "createHabit/{habitId}",
            arguments = listOf(
                navArgument("habitId") { type = NavType.IntType; defaultValue = -1 }
            )
        ) { backStackEntry ->
            val habitId = backStackEntry.arguments?.getInt("habitId")?.takeIf { it != -1 }
            CreateHabitScreen(
                habitId = habitId,
                onHabitCreated = { navController.popBackStack() }
            )
        }

        // 👉 Detalles y edición de hábitos
        composable("habitDetails/{habitId}") { backStackEntry ->
            val habitId = backStackEntry.arguments?.getString("habitId")?.toIntOrNull()
                ?: return@composable
            HabitDetailsScreen(
                habitId = habitId,
                onHabitUpdated = { navController.popBackStack() }
            )
        }

        composable("editHabit/{habitId}") { backStackEntry ->
            val habitId = backStackEntry.arguments?.getString("habitId")?.toIntOrNull()
                ?: return@composable
            EditHabitScreen(
                habitId = habitId,
                onHabitUpdated = { navController.popBackStack() }
            )
        }

        // 👉 Pantallas de premium, estadísticas y rutinas
        composable("premiumOffer") {
            PremiumVersionOfferScreen(onContinue = { navController.navigate("main") })
        }

        composable("stats") {
            StatsScreen()
        }

        composable("routines") {
            RecommendedRoutinesScreen(navController)
        }

        composable(
            route = "routineDetail/{routineId}",
            arguments = listOf(
                navArgument("routineId") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val routineId = backStackEntry.arguments?.getString("routineId") ?: ""
            RoutineDetailScreen(
                routineId = routineId,
                onRoutineAdded = { navController.popBackStack() }
            )
        }
    }
}
