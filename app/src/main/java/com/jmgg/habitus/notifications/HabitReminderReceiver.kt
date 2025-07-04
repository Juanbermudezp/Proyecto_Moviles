package com.jmgg.habitus.notifications

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.jmgg.habitus.R

class HabitReminderReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val habitName = intent.getStringExtra("habit_name") ?: "Tu hábito"
        val notification = NotificationCompat.Builder(context, "habit_reminder_channel")
            .setSmallIcon(R.drawable.list_icon)
            .setContentTitle("Recordatorio de hábito")
            .setContentText("¡Hora de trabajar en: $habitName!")
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .build()

        val notificationManager = NotificationManagerCompat.from(context)
        if (notificationManager.areNotificationsEnabled()) {
            notificationManager.notify(habitName.hashCode(), notification)
        }
    }

}
