package com.deanu.githubuser.common.data.cache

import com.deanu.githubuser.common.data.cache.daos.GitHubUserDao
import com.deanu.githubuser.common.data.cache.model.CachedGitHubUser
import javax.inject.Inject

class RoomCache @Inject constructor(private val gitHubUserDao: GitHubUserDao) : Cache {
    override suspend fun storeFavoriteUser(cachedGitHubUser: CachedGitHubUser) {
        gitHubUserDao.insertFavoriteUser(cachedGitHubUser)
    }

    override suspend fun getFavoriteUsers(): List<CachedGitHubUser> =
        gitHubUserDao.getFavoriteUserList()

    override suspend fun deleteFavoriteUser(userId: Int) {
        gitHubUserDao.deleteFavoriteUser(userId)
    }
}