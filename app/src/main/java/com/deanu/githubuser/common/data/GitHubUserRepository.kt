package com.deanu.githubuser.common.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import com.deanu.githubuser.common.data.api.GitHubUserApi
import com.deanu.githubuser.common.data.api.model.mappers.ApiFollowersMapper
import com.deanu.githubuser.common.data.api.model.mappers.ApiUserDetailMapper
import com.deanu.githubuser.common.data.api.model.mappers.ApiUserMapper
import com.deanu.githubuser.common.data.cache.Cache
import com.deanu.githubuser.common.data.cache.model.CachedGitHubUser
import com.deanu.githubuser.common.data.preferences.Preferences
import com.deanu.githubuser.common.domain.model.User
import com.deanu.githubuser.common.domain.model.UserDetail
import com.deanu.githubuser.common.domain.repository.AppSettingRepository
import com.deanu.githubuser.common.domain.repository.UserRepository
import javax.inject.Inject

class GitHubUserRepository @Inject constructor(
    private val api: GitHubUserApi,
    private val cache: Cache,
    private val preferences: Preferences,
    private val apiUserMapper: ApiUserMapper,
    private val apiUserDetailMapper: ApiUserDetailMapper,
    private val apiFollowersMapper: ApiFollowersMapper,
) : UserRepository, AppSettingRepository {

    override suspend fun getUserList(username: String): List<User> {
        return api.getGithubUserList(username).items?.map { apiUser ->
            apiUserMapper.mapToDomain(apiUser)
        }.orEmpty()
    }

    override suspend fun getFavoriteUserList(): List<UserDetail> {
        val favoriteUserList = cache.getFavoriteUsers()
        return favoriteUserList.map { cachedGitHubUser ->
            cachedGitHubUser.toDomain()
        }
    }

    override suspend fun getUserDetail(username: String): UserDetail {
        val apiUserDetail = api.getGithubUserDetail(username)
        return apiUserDetailMapper.mapToDomain(apiUserDetail)
    }

    override suspend fun getFavoriteUserDetail(username: String): UserDetail {
        return cache.getFavoriteUserDetail(username).toDomain()
    }

    override suspend fun getUserFollowers(username: String): List<User> {
        return api.getGithubUserFollowersList(username).map { followersList ->
            apiFollowersMapper.mapToDomain(followersList)
        }
    }

    override suspend fun getUserFollowing(username: String): List<User> {
        return api.getGithubUserFollowingList(username).map { followingList ->
            apiFollowersMapper.mapToDomain(followingList)
        }
    }

    override suspend fun storeFavoriteUser(userDetail: UserDetail) {
        cache.storeFavoriteUser(CachedGitHubUser.fromDomain(userDetail))
    }

    override suspend fun deleteFavoriteUser(userId: Int) {
        cache.deleteFavoriteUser(userId)
    }

    override fun getAppMode(): LiveData<Boolean> {
        return preferences.getAppMode().asLiveData()
    }

    override suspend fun setAppMode(isDarkMode: Boolean) {
        preferences.setAppMode(isDarkMode)
    }
}