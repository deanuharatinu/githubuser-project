package com.deanu.githubuser.common.data.cache

import com.deanu.githubuser.common.data.cache.model.CachedGitHubUser

interface Cache {
    suspend fun storeFavoriteUser(cachedGitHubUser: CachedGitHubUser)

    suspend fun getFavoriteUsers(): List<CachedGitHubUser>

    suspend fun getFavoriteUserDetail(username: String): CachedGitHubUser

    suspend fun deleteFavoriteUser(userId: Int)
}