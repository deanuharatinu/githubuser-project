package com.deanu.githubuser.common.data.api.model.mappers

import com.deanu.githubuser.common.data.api.model.ApiUserDetail
import com.deanu.githubuser.common.domain.model.UserDetail
import javax.inject.Inject

class ApiUserDetailMapper @Inject constructor() : ApiMapper<ApiUserDetail?, UserDetail> {
    override fun mapToDomain(apiEntity: ApiUserDetail?): UserDetail {
        return UserDetail(
            id = apiEntity?.id ?: 0,
            username = apiEntity?.login.orEmpty(),
            name = apiEntity?.name.orEmpty(),
            avatarUrl = apiEntity?.avatarUrl.orEmpty(),
            githubUrl = apiEntity?.htmlUrl.orEmpty(),
            followers = apiEntity?.followers ?: 0,
            following = apiEntity?.following ?: 0
        )
    }
}