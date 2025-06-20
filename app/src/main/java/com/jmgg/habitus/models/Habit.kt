package com.jmgg.habitus.models

import com.google.gson.annotations.SerializedName

data class Habit(
    val id: Int? = null,

    @SerializedName("user_id")
    val userId: Int,

    val name: String,
    val category: String,
    val frequency: String,

    @SerializedName("reminder_time")
    val reminderTime: String? = null,

    val description: String = "",
    val notes: String = ""


)
