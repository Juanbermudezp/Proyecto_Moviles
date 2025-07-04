package com.jmgg.habitus.utils

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import com.jmgg.habitus.notifications.HabitReminderReceiver
import java.util.*

object AlarmScheduler {

    fun scheduleHabitReminder(context: Context, hour: Int, minute: Int, habitName: String) {
        val calendar = Calendar.getInstance().apply {
            timeInMillis = System.currentTimeMillis()
            set(Calendar.HOUR_OF_DAY, hour)
            set(Calendar.MINUTE, minute)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)

            if (before(Calendar.getInstance())) {
                add(Calendar.DAY_OF_YEAR, 1)
            }
        }

        val intent = Intent(context, HabitReminderReceiver::class.java).apply {
            putExtra("habit_name", habitName)
        }

        val pendingIntent = PendingIntent.getBroadcast(
            context,
            habitName.hashCode(),
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            if (!alarmManager.canScheduleExactAlarms()) {
                Log.w("AlarmScheduler", "No se puede programar alarma exacta. Requiere permiso.")
                return
            }
        }

        try {
            alarmManager.setExactAndAllowWhileIdle(
                AlarmManager.RTC_WAKEUP,
                calendar.timeInMillis,
                pendingIntent
            )
            Log.d("AlarmScheduler", "Alarma programada para $hour:$minute")
        } catch (e: SecurityException) {
            Log.e("AlarmScheduler", "No se pudo programar la alarma exacta: ${e.message}")
        }
    }
}
