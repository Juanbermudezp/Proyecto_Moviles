package com.jmgg.habitus

import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import com.jmgg.habitus.navigation.AppScaffold
import com.jmgg.habitus.ui.theme.HabitusTheme
import com.jmgg.habitus.utils.NotificationUtils

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        NotificationUtils.createNotificationChannel(this)
        
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (checkSelfPermission(android.Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(arrayOf(android.Manifest.permission.POST_NOTIFICATIONS), 1001)
            }
        }

        enableEdgeToEdge()
        setContent {
            HabitusTheme {
                Surface(color = MaterialTheme.colorScheme.background) {
                    AppScaffold()
                }
            }
        }
    }
}
