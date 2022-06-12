package com.deanu.githubuser.common.data.cache.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.deanu.githubuser.common.data.cache.model.CachedGitHubUser

@Dao
interface GitHubUserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavoriteUser(favoriteUser: CachedGitHubUser)

    @Query("SELECT * FROM favorited_user")
    fun getFavoriteUserList(): List<CachedGitHubUser>

    @Query("DELETE FROM favorited_user WHERE id = :userId")
    fun deleteFavoriteUser(userId: Int)
}