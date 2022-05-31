package com.deanu.githubuser.common.domain.repository

import com.deanu.githubuser.common.domain.model.User
import com.deanu.githubuser.common.domain.model.UserDetail

interface UserRepository {
    suspend fun getUserList(username: String): List<User>

    suspend fun getUserDetail(username: String): UserDetail

    suspend fun getUserFollowers(username:String): List<User>

    suspend fun getUserFollowing(username: String) : List<User>
}