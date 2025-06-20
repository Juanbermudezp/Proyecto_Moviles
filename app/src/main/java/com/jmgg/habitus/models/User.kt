package com.jmgg.habitus.models

data class User(
    val id: Int = 0,
    val name: String,
    val email: String,
    val password: String,
    val isPremium: Boolean = false
)