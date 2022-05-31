package com.deanu.githubuser.common.domain.model

data class UserDetail(
    val id: Int,
    val username: String,
    val name: String,
    val avatarUrl: String,
    val githubUrl: String,
    val followers: Int,
    val following: Int
)