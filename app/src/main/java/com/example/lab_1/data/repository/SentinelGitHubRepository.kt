package com.example.lab_1.data.repository

import com.example.lab_1.BuildConfig
import com.example.lab_1.data.api.SentinelGitHubApi
import com.example.lab_1.data.model.GitHubCommentInfo

class SentinelGitHubRepository(
    private val api: SentinelGitHubApi
) {

    private val token =
        "Bearer ${BuildConfig.GITHUB_TOKEN}"

    suspend fun getCommentsFromOpenPullRequests(
        owner: String,
        repo: String
    ): List<GitHubCommentInfo> {

        val pullRequests =
            api.getOpenPullRequests(
                authorization = token,
                owner = owner,
                repo = repo
            )

        return pullRequests.flatMap { pullRequest ->

            api.getComments(
                authorization = token,
                owner = owner,
                repo = repo,
                issueNumber = pullRequest.number
            )
        }
    }
}