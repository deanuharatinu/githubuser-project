package com.deanu.githubuser.common.data.cache

import androidx.room.Database
import androidx.room.RoomDatabase
import com.deanu.githubuser.common.data.cache.daos.GitHubUserDao
import com.deanu.githubuser.common.data.cache.model.CachedGitHubUser

@Database(entities = [CachedGitHubUser::class], version = 1, exportSchema = false)
abstract class GitHubUserDatabase : RoomDatabase() {
    abstract fun gitHubUserDao(): GitHubUserDao
}