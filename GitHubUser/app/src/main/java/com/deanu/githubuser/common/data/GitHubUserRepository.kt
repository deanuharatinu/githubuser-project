package com.deanu.githubuser.common.data

import com.deanu.githubuser.common.data.api.GitHubUserApi
import com.deanu.githubuser.common.data.api.model.mappers.ApiFollowersMapper
import com.deanu.githubuser.common.data.api.model.mappers.ApiUserDetailMapper
import com.deanu.githubuser.common.data.api.model.mappers.ApiUserMapper
import com.deanu.githubuser.common.domain.model.User
import com.deanu.githubuser.common.domain.model.UserDetail
import com.deanu.githubuser.common.domain.repository.UserRepository
import javax.inject.Inject

class GitHubUserRepository @Inject constructor(
    private val api: GitHubUserApi,
    private val apiUserMapper: ApiUserMapper,
    private val apiUserDetailMapper: ApiUserDetailMapper,
    private val apiFollowersMapper: ApiFollowersMapper
) : UserRepository {

    override suspend fun getUserList(username: String): List<User> {
        return api.getGithubUserList(username).items?.map { apiUser ->
            apiUserMapper.mapToDomain(apiUser)
        }.orEmpty()
    }

    override suspend fun getUserDetail(username: String): UserDetail {
        val apiUserDetail = api.getGithubUserDetail(username)
        return apiUserDetailMapper.mapToDomain(apiUserDetail)
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
}