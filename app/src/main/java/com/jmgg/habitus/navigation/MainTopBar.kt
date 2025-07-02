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
        title = { Text(text = routeToTitle(currentRoute)) },
        navigationIcon = {
            Box {
                IconButton(onClick = { expanded = true }) {
                    Icon(Icons.Default.Menu, contentDescription = "Menú")
                }

                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false },
                    offset = DpOffset(x = 0.dp, y = 0.dp)
                ) {
                    DropdownMenuItem(
                        text = { Text("Inicio") },
                        onClick = {
                            expanded = false
                            onNavigateToHome()
                        },
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Default.Home,
                                contentDescription = "Inicio"
                            )
                        }
                    )

                    if (isPremium) {
                        DropdownMenuItem(
                            text = { Text("Estadísticas") },
                            onClick = {
                                expanded = false
                                onNavigateToStats()
                            },
                            leadingIcon = {
                                Icon(
                                    imageVector = Icons.Default.BarChart,
                                    contentDescription = "Estadísticas"
                                )
                            }
                        )

                        DropdownMenuItem(
                            text = { Text("Rutinas") },
                            onClick = {
                                expanded = false
                                onNavigateToRoutines()
                            },
                            leadingIcon = {
                                Icon(
                                    imageVector = Icons.Default.Star,
                                    contentDescription = "Rutinas"
                                )
                            }
                        )
                    }

                    Divider()

                    DropdownMenuItem(
                        text = { Text("Salir") },
                        onClick = {
                            expanded = false
                            onLogout()
                        },
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Default.ExitToApp,
                                contentDescription = "Cerrar sesión"
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
