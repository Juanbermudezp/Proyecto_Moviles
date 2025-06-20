package com.jmgg.habitus.data.datasources

import com.jmgg.habitus.models.User

interface UserDataSource{
    suspend fun registerUser(user: User): Boolean
    suspend fun loginUser(email: String, password: String): User?
    suspend fun getUserById(userId: Int): User?
}