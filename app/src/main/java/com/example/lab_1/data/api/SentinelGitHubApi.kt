package com.example.lab_1.data.api

import com.example.lab_1.data.model.GitHubCommentInfo
import com.example.lab_1.data.model.PullRequestInfo
import com.example.lab_1.data.model.UpdatePullRequestRequest
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.PATCH
import retrofit2.http.Path
import retrofit2.http.Query
import com.example.lab_1.data.model.GitHubFileResponse
import com.example.lab_1.data.model.UpdateFileRequest
import retrofit2.http.PUT

interface SentinelGitHubApi {

    @GET("repos/{owner}/{repo}/pulls")
    suspend fun getOpenPullRequests(
        @Header("Authorization") authorization: String,
        @Path("owner") owner: String,
        @Path("repo") repo: String,
        @Query("state") state: String = "open"
    ): List<PullRequestInfo>

    @GET("repos/{owner}/{repo}/issues/{issueNumber}/comments")
    suspend fun getComments(
        @Header("Authorization") authorization: String,
        @Path("owner") owner: String,
        @Path("repo") repo: String,
        @Path("issueNumber") issueNumber: Int
    ): List<GitHubCommentInfo>

    @PATCH("repos/{owner}/{repo}/pulls/{pullNumber}")
    suspend fun updatePullRequest(
        @Header("Authorization") authorization: String,
        @Path("owner") owner: String,
        @Path("repo") repo: String,
        @Path("pullNumber") pullNumber: Int,
        @Body request: UpdatePullRequestRequest
    ): PullRequestInfo

    @GET("repos/{owner}/{repo}/contents/house_config.json")
    suspend fun getHouseConfigFile(
        @Header("Authorization") authorization: String,
        @Path("owner") owner: String,
        @Path("repo") repo: String,
        @Query("ref") branch: String = "main"
    ): GitHubFileResponse

    @PUT("repos/{owner}/{repo}/contents/house_config.json")
    suspend fun updateHouseConfigFile(
        @Header("Authorization") authorization: String,
        @Path("owner") owner: String,
        @Path("repo") repo: String,
        @Body request: UpdateFileRequest
    )
}