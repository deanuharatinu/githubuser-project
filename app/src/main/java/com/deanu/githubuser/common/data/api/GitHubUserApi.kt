package com.deanu.githubuser.common.data.api

import com.deanu.githubuser.common.data.api.model.ApiFollowers
import com.deanu.githubuser.common.data.api.model.ApiUserDetail
import com.deanu.githubuser.common.data.api.model.ApiUserList
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GitHubUserApi {
    @GET(ApiConstants.SEARCH_USER_ENDPOINT)
    suspend fun getGithubUserList(
        @Query(ApiParameters.SEARCH) username: String
    ): ApiUserList

    @GET(ApiConstants.USER_DETAIL_ENDPOINT + "{username}")
    suspend fun getGithubUserDetail(
        @Path("username") username: String
    ): ApiUserDetail

    @GET(ApiConstants.USER_DETAIL_ENDPOINT + "{username}/" + ApiParameters.FOLLOWERS)
    suspend fun getGithubUserFollowersList(
        @Path("username") username: String
    ): List<ApiFollowers>

    @GET(ApiConstants.USER_DETAIL_ENDPOINT + "{username}/" + ApiParameters.FOLLOWING)
    suspend fun getGithubUserFollowingList(
        @Path("username") username: String
    ) : List<ApiFollowers>
}