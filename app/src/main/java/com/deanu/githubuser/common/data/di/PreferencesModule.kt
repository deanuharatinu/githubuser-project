package com.deanu.githubuser.common.data.di

import com.deanu.githubuser.common.data.preferences.GitHubUserPreferences
import com.deanu.githubuser.common.data.preferences.Preferences
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class PreferencesModule {

    @Binds
    abstract fun providePreferences(preferences: GitHubUserPreferences): Preferences
}