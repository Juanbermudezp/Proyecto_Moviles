package com.jmgg.habitus.network

import com.jmgg.habitus.models.Habit
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Query

interface SupabaseApiService{


    @POST("habits")
    suspend fun insertHabit(
        @Body habit: Habit,
        @Header("Authorization") authHeader: String,
        @Header("apikey") apiKey: String
    )

    @GET("habits")
    suspend fun getHabits(
        @Header("Authorization") authHeader: String,
        @Header("apikey") apiKey: String,
        @Query("user_id") userId: String
    ): List<Habit>

    @GET("habits")
    suspend fun getHabitById(
        @Query("id") habitId: String,
        @Header("Authorization") authHeader: String,
        @Header("apikey") apiKey: String
    ): List<Habit>


    @DELETE("habits")
    suspend fun deleteHabit(
        @Query("id") habitId: String,
        @Header("Authorization") authHeader: String,
        @Header("apikey") apiKey: String
    ): Response<Unit>

    @PATCH("habits")
    suspend fun updateHabit(
        @Query("id") habitId: String,
        @Body habit: Habit,
        @Header("Authorization") authHeader: String,
        @Header("apikey") apiKey: String
    ): Response<Unit>
}