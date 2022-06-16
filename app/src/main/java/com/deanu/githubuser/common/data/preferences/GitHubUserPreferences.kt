package com.deanu.githubuser.common.data.preferences

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import com.deanu.githubuser.common.data.preferences.GitHubUserPreferences.Companion.PREFERENCES_NAME
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

private val Context.dataStore by preferencesDataStore(PREFERENCES_NAME)

@Singleton
class GitHubUserPreferences @Inject constructor(
    @ApplicationContext context: Context
) : Preferences {

    private val settingsDataStore = context.dataStore

    override suspend fun setAppMode(isDarkMode: Boolean) {
        settingsDataStore.edit { preferences ->
            preferences[THEME_KEY] = isDarkMode
        }
    }

    override fun getAppMode(): Flow<Boolean> {
        return settingsDataStore.data.map { preferences ->
            preferences[THEME_KEY] ?: false
        }
    }

    companion object {
        const val PREFERENCES_NAME = "GITHUB_USER_PREFERENCES"
        private val THEME_KEY = booleanPreferencesKey("theme_setting")
    }
}