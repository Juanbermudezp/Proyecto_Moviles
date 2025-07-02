package com.jmgg.habitus

import android.app.Application
import com.jmgg.habitus.data.datasources.remote.SupabaseHabitDataSource
import com.jmgg.habitus.data.repositories.HabitRepository
import com.jmgg.habitus.data.repositories.UserRepository
import com.jmgg.habitus.viewmodel.AuthViewModel
import com.jmgg.habitus.viewmodel.HabitViewModel
import com.jmgg.habitus.network.SupabaseApiService
import com.jmgg.habitus.network.SupabaseClient
import androidx.room.Room
import com.jmgg.habitus.data.datasources.local.AppDatabase
import com.jmgg.habitus.data.datasources.local.LocalUserDataSource
import androidx.lifecycle.ViewModelProvider // Or your DI framework's way to get ViewModels


class HabitusApp : Application() {

    companion object {
        private lateinit var instance: HabitusApp

        private lateinit var habitRepository: HabitRepository
        private lateinit var userRepository: UserRepository

        lateinit var habitViewModel: HabitViewModel
            private set

        lateinit var authViewModel: AuthViewModel
            private set
    }

    override fun onCreate() {
        super.onCreate()

        val db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "habitus_db"
        )
            .fallbackToDestructiveMigration()
            .build()

        val userDao = db.userDao()
        val localUserDataSource = LocalUserDataSource(userDao)
        userRepository = UserRepository(localUserDataSource)

        val api = SupabaseClient.retrofit.create(SupabaseApiService::class.java)
        val supabaseDataSource = SupabaseHabitDataSource(api, "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6InpndW5zb3d0YnR5bm1lZmZxYXRpIiwicm9sZSI6ImFub24iLCJpYXQiOjE3NDg5NzMwNDAsImV4cCI6MjA2NDU0OTA0MH0.WaeTqgQLECej8NmjJyXyix0z9NPj1in4O4vyB4ppvFw")
        habitRepository = HabitRepository(supabaseDataSource)

        habitViewModel = HabitViewModel(habitRepository)
        authViewModel = AuthViewModel(userRepository)
    }

}
