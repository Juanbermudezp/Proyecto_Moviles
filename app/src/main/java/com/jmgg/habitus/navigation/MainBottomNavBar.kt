package com.jmgg.habitus.ui.main.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

@Composable
fun MainBottomNavBar(
    isPremium: Boolean,
    currentRoute: String?,
    onNavigateToHome: () -> Unit,
    onNavigateToStats: () -> Unit,
    onNavigateToRoutines: () -> Unit,
    onNavigateToOfert: () -> Unit
) {

    NavigationBar (
        containerColor = Color(0xFF475569)
    ) {
        NavigationBarItem(
            selected = currentRoute == "main",
            onClick = onNavigateToHome,
            icon = {
                Icon(
                    imageVector = Icons.Default.Home,
                    contentDescription = "Inicio",
                    tint = if (currentRoute == "main") Color(0xFF34D399) else Color(0xFF6366F1)
                )
            },
            label = { Text("Inicio",
                color = Color(0xFFF8FAFC)
            ) }
        )
        if (isPremium) {
            NavigationBarItem(
                selected = currentRoute == "stats",
                onClick = onNavigateToStats,
                icon = {
                    Icon(
                        imageVector = Icons.Default.BarChart,
                        contentDescription = "Estadísticas",
                        tint = if (currentRoute == "stats") Color(0xFF34D399) else Color(0xFF6366F1)
                    )
                },
                label = { Text("Estadísticas",
                    color = Color(0xFFF8FAFC))
                }
            )

            NavigationBarItem(
                selected = currentRoute == "routines",
                onClick = onNavigateToRoutines,
                icon = {
                    Icon(
                        imageVector = Icons.Default.Star,
                        contentDescription = "Recomendaciones",
                        tint = if (currentRoute == "routines") Color(0xFF34D399) else Color(0xFF6366F1)
                    )
                },
                label = { Text("Rutinas",
                    color = Color(0xFFF8FAFC))
                }
            )
        }
        if(!isPremium){
            NavigationBarItem(
                selected = currentRoute == "premiumOffer",
                onClick = onNavigateToOfert,
                icon = {
                    Icon(
                        imageVector = Icons.Default.BarChart,
                        contentDescription = "Estadísticas",
                        tint = if (currentRoute == "stats") Color(0xFF34D399) else Color(0xFF6366F1)
                    )
                },
                label = { Text("Estadísticas",
                    color = Color(0xFFF8FAFC))
                }
            )

            NavigationBarItem(
                selected = currentRoute == "premiumOffer",
                onClick = onNavigateToOfert,
                icon = {
                    Icon(
                        imageVector = Icons.Default.Star,
                        contentDescription = "Recomendaciones",
                        tint = if (currentRoute == "routines") Color(0xFF34D399) else Color(0xFF6366F1)
                    )
                },
                label = { Text("Rutinas",
                    color = Color(0xFFF8FAFC))

                }
            )
        }

    }
}
