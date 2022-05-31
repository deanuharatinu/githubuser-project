package com.deanu.githubuser.common.data.api.model.mappers

import com.deanu.githubuser.common.data.api.model.ApiFollowers
import com.deanu.githubuser.common.domain.model.User
import javax.inject.Inject

class ApiFollowersMapper @Inject constructor() : ApiMapper<ApiFollowers?, User> {
    override fun mapToDomain(apiEntity: ApiFollowers?): User {
        return User(
            username = apiEntity?.login.orEmpty(),
            avatarUrl = apiEntity?.avatarUrl.orEmpty(),
            githubUrl = apiEntity?.htmlUrl.orEmpty(),
            userId = apiEntity?.id ?: 0
        )
    }
}