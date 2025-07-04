package com.jmgg.habitus.data.datasources.local

import com.jmgg.habitus.data.datasources.UserDataSource
import com.jmgg.habitus.data.datasources.local.dao.UserDao
import com.jmgg.habitus.data.datasources.local.entities.UserEntity
import com.jmgg.habitus.models.User

class LocalUserDataSource(private val userDao: UserDao) : UserDataSource {
    override suspend fun registerUser(user: User): Boolean {
        val entity = UserEntity(
            name = user.name,
            email = user.email,
            password = user.password,
            isPremium = user.isPremium
        )
        return userDao.insert(entity) > 0
    }

    override suspend fun loginUser(email: String, password: String): User? {
        val entity = userDao.login(email, password) ?: return null
        return entity.toModel()
    }

    override suspend fun getUserById(userId: Int): User? {
        val entity = userDao.getUserById(userId) ?: return null
        return entity.toModel()
    }

    private fun UserEntity.toModel() = User(id, name, email, password, isPremium)
}