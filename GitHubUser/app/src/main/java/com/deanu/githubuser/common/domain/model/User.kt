package com.deanu.githubuser.common.domain.model

data class User(
    val username: String,
    val avatarUrl: String,
    val githubUrl: String,
    val userId: Int
)