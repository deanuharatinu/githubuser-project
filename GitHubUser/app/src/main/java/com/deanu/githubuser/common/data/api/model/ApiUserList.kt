package com.deanu.githubuser.common.data.api.model

import com.google.gson.annotations.SerializedName

data class ApiUserList(
    @field:SerializedName("total_count")
    val totalCount: Int? = null,
    @field:SerializedName("incomplete_results")
    val incompleteResults: Boolean? = null,
    @field:SerializedName("items")
    val items: List<ApiUser?>? = null
)

data class ApiUser(
    @field:SerializedName("avatar_url")
    val avatarUrl: String? = null,
    @field:SerializedName("id")
    val id: Int? = null,
    @field:SerializedName("login")
    val login: String? = null,
    @field:SerializedName("url")
    val githubUrl: String? = null
)
