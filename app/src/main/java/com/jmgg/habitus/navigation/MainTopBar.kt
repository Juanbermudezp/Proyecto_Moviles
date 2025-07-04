package com.jmgg.habitus.ui.main.components

import androidx.compose.foundation.layout.Box
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.DpOffset

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainTopBar(
    isPremium: Boolean,
    currentRoute: String?,
    onNavigateToHome: () -> Unit,
    onNavigateToStats: () -> Unit,
    onNavigateToRoutines: () -> Unit,
    onLogout: () -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    TopAppBar(
        title = {
            Text(
                text = routeToTitle(currentRoute),
                color = Color(0xFFF8FAFC)
            ) },

        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color(0xFF0F172A),
            navigationIconContentColor = Color(0xFF6366F1),
            titleContentColor = Color(0xFFF8FAFC)
        ),

        navigationIcon = {
            Box {
                IconButton(onClick = { expanded = true }) {
                    Icon(Icons.Default.Menu,
                        contentDescription = "Menú",
                        tint = Color(0xFF6366F1)
                    )
                }

                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false },
                    offset = DpOffset(x = 0.dp, y = 0.dp),
                    containerColor = Color(0xFF475569)
                ) {
                    DropdownMenuItem(
                        text = { Text("Inicio",
                            color = Color(0xFFF8FAFC)) },
                        onClick = {
                            expanded = false
                            onNavigateToHome()
                        },
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Default.Home,
                                contentDescription = "Inicio",
                                tint = Color(0xFF6366F1)
                            )
                        }
                    )

                    if (isPremium) {
                        DropdownMenuItem(
                            text = { Text("Estadísticas",
                                color = Color(0xFFF8FAFC))},
                            onClick = {
                                expanded = false
                                onNavigateToStats()
                            },
                            leadingIcon = {
                                Icon(
                                    imageVector = Icons.Default.BarChart,
                                    contentDescription = "Estadísticas",
                                    tint = Color(0xFF6366F1)
                                )
                            }
                        )

                        DropdownMenuItem(
                            text = { Text("Rutinas",
                                color = Color(0xFFF8FAFC)) },
                            onClick = {
                                expanded = false
                                onNavigateToRoutines()
                            },
                            leadingIcon = {
                                Icon(
                                    imageVector = Icons.Default.Star,
                                    contentDescription = "Rutinas",
                                    tint = Color(0xFF6366F1)
                                )
                            }
                        )
                    }

                    Divider(color = Color(0xFF334155))

                    DropdownMenuItem(
                        text = { Text("Salir",
                            color = Color(0xFFF8FAFC)) },
                        onClick = {
                            expanded = false
                            onLogout()
                        },
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Default.ExitToApp,
                                contentDescription = "Cerrar sesión",
                                tint = Color(0xFFEF4444)
                            )
                        }
                    )
                }
            }
        }
    )
}

private fun routeToTitle(route: String?): String {
    return when (route) {
        "main" -> "Inicio"
        "stats" -> "Estadísticas"
        "routines" -> "Rutinas"
        "profile" -> "Perfil"
        else -> "Habitus"
    }
}
