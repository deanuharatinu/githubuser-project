package com.deanu.githubuser.common.data.api.model.mappers

import com.deanu.githubuser.common.data.api.model.ApiUser
import com.deanu.githubuser.common.domain.model.User
import javax.inject.Inject

class ApiUserMapper @Inject constructor() : ApiMapper<ApiUser?, User> {
    override fun mapToDomain(apiEntity: ApiUser?): User {
        return User(
            username = apiEntity?.login.orEmpty(),
            avatarUrl = apiEntity?.avatarUrl.orEmpty(),
            githubUrl = apiEntity?.githubUrl.orEmpty(),
            userId = apiEntity?.id ?: 0
        )
    }
}