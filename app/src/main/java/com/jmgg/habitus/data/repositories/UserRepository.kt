package com.jmgg.habitus.data.repositories

import com.jmgg.habitus.data.datasources.UserDataSource
import com.jmgg.habitus.models.User

class UserRepository(private val dataSource: UserDataSource) {

    suspend fun registerUser(user: User): Boolean = dataSource.registerUser(user)

    suspend fun loginUser(email: String, password: String): User? = dataSource.loginUser(email, password)

    suspend fun getUserById(userId: Int): User? = dataSource.getUserById(userId)
}