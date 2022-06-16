package com.deanu.githubuser.common.domain.repository

import androidx.lifecycle.LiveData

interface AppSettingRepository {
    fun getAppMode(): LiveData<Boolean>

    suspend fun setAppMode(isDarkMode:Boolean)
}