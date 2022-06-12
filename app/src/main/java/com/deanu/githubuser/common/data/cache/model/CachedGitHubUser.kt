package com.deanu.githubuser.common.data.cache.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.deanu.githubuser.common.domain.model.UserDetail

@Entity(tableName = "favorited_user")
data class CachedGitHubUser(
    @PrimaryKey
    val id: Int,
    val username: String,
    val name: String,
    val avatarUrl: String,
    val githubUrl: String,
    val followers: Int,
    val following: Int
) {
    companion object {
        fun fromDomain(userDetail: UserDetail): CachedGitHubUser {
            return CachedGitHubUser(
                id = userDetail.id,
                username = userDetail.username,
                name = userDetail.username,
                avatarUrl = userDetail.avatarUrl,
                githubUrl = userDetail.githubUrl,
                followers = userDetail.followers,
                following = userDetail.following
            )
        }
    }

    fun toDomain(): UserDetail =
        UserDetail(id, username, name, avatarUrl, githubUrl, followers, following)
}
