package com.jmgg.habitus.context

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


val Context.dataStore: DataStore<Preferences> by preferencesDataStore("settings")
private val ONBOARDING_KEY = booleanPreferencesKey("onboarding_completed")

suspend fun markOnboardingComplete(context: Context) {
    context.dataStore.edit { it[ONBOARDING_KEY] = true }
}

fun Flow<Preferences>.isOnboardingComplete(): Flow<Boolean> =
    map { prefs -> prefs[ONBOARDING_KEY] ?: false }
