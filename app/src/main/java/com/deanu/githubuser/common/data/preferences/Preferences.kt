package com.deanu.githubuser.common.data.preferences

import kotlinx.coroutines.flow.Flow

interface Preferences {
    suspend fun setAppMode(isDarkMode: Boolean)

    fun getAppMode(): Flow<Boolean>
}