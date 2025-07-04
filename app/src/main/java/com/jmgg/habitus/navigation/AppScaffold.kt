package com.jmgg.habitus.navigation

import android.annotation.SuppressLint
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
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import com.jmgg.habitus.Context.dataStore
import com.jmgg.habitus.Context.isOnboardingComplete


@SuppressLint("FlowOperatorInvokedInComposition", "UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun AppScaffold() {
    val navController = rememberNavController()
    val context = LocalContext.current
    val currentUser = HabitusApp.authViewModel.currentUser.collectAsState().value

    val alreadyDone by context.dataStore.data
        .isOnboardingComplete()
        .collectAsState(initial = false)

    val startDestination = if (!alreadyDone || currentUser == null) {
        "intro"
    } else {
        "main"
    }

    val currentRoute = navController.currentBackStackEntryFlow
        .collectAsState(null).value?.destination?.route
    val hideBars = currentRoute in listOf("login", "register", "intro")

    Scaffold(
        containerColor = Color(0xFF0F172A),
        contentWindowInsets = WindowInsets(0), // Pantalla completa
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
                    onNavigateToOfert = { navController.navigate("premiumOffer") }
                )
            }
        },
        floatingActionButton = {
            if (currentRoute == "main") {
                FloatingActionButton(
                    containerColor = Color(0xFF34D399),
                    contentColor = Color(0xFF0F172A),
                    onClick = { navController.navigate("createHabit") }
                ) {
                    Text("+")
                }
            }
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(Color(0xFF0F172A))
        ) {
            AppNavHost(
                navController = navController,
                startDestination = startDestination,
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}


