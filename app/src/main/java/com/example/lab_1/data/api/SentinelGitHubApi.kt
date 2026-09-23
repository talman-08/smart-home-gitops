package com.example.lab_1.data.api

import com.example.lab_1.data.model.GitHubCommentInfo
import com.example.lab_1.data.model.PullRequestInfo
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

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
}