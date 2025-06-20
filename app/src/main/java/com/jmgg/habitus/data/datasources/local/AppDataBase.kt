package com.jmgg.habitus.data.datasources.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.jmgg.habitus.data.datasources.local.dao.UserDao
import com.jmgg.habitus.data.datasources.local.entities.UserEntity

@Database(
    entities = [UserEntity::class],
    version = 3,
    exportSchema = false // Opcional: evita que Room genere archivos de esquema en /schemas
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
}