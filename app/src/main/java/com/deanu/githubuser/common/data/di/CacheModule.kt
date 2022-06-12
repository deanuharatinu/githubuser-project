package com.deanu.githubuser.common.data.di

import android.content.Context
import androidx.room.Room
import com.deanu.githubuser.common.data.cache.Cache
import com.deanu.githubuser.common.data.cache.GitHubUserDatabase
import com.deanu.githubuser.common.data.cache.RoomCache
import com.deanu.githubuser.common.data.cache.daos.GitHubUserDao
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class CacheModule {

    @Binds
    abstract fun bindCache(cache: RoomCache): Cache

    companion object {
        @Provides
        @Singleton
        fun provideDatabase(@ApplicationContext context: Context): GitHubUserDatabase {
            return Room.databaseBuilder(
                context,
                GitHubUserDatabase::class.java,
                "github-user.db"
            ).build()
        }

        @Provides
        fun provideGitHubUserDao(gitHubUserDatabase: GitHubUserDatabase): GitHubUserDao {
            return gitHubUserDatabase.gitHubUserDao()
        }
    }
}