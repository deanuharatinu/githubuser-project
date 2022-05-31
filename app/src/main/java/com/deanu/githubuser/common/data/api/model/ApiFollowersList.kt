package com.deanu.githubuser.common.data.api.model

import com.google.gson.annotations.SerializedName

data class ApiFollowers(
    @field:SerializedName("login")
    val login: String? = null,
    @field:SerializedName("avatar_url")
    val avatarUrl: String? = null,
    @field:SerializedName("html_url")
    val htmlUrl: String? = null,
    @field:SerializedName("id")
    val id: Int? = null,
)
