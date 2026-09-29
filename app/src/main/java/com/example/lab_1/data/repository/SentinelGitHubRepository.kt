package com.example.lab_1.data.repository

import android.util.Base64
import com.example.lab_1.BuildConfig
import com.example.lab_1.data.api.SentinelGitHubApi
import com.example.lab_1.data.model.GitHubCommentInfo
import com.example.lab_1.data.model.HouseConfig
import com.example.lab_1.data.model.UpdateFileRequest
import com.example.lab_1.data.model.UpdatePullRequestRequest
import com.google.gson.Gson

class SentinelGitHubRepository(
    private val api: SentinelGitHubApi
) {

    private val token =
        "Bearer ${BuildConfig.GITHUB_TOKEN}"

    private val gson = Gson()

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
            ).map { comment ->
                comment.copy(
                    prNumber = pullRequest.number
                )
            }
        }
    }

    suspend fun rejectPullRequest(
        owner: String,
        repo: String,
        prNumber: Int
    ) {
        api.updatePullRequest(
            authorization = token,
            owner = owner,
            repo = repo,
            pullNumber = prNumber,
            request = UpdatePullRequestRequest(
                state = "closed"
            )
        )
    }

    suspend fun forceMerge(
        owner: String,
        repo: String,
        prNumber: Int
    ) {
        // 1. Get the current house_config.json from main.
        val fileResponse = api.getHouseConfigFile(
            authorization = token,
            owner = owner,
            repo = repo,
            branch = "main"
        )

        // 2. GitHub returns file content encoded as Base64.
        val decodedBytes = Base64.decode(
            fileResponse.content,
            Base64.DEFAULT
        )

        val json = String(decodedBytes, Charsets.UTF_8)

        // 3. Convert the JSON into our Kotlin model.
        val currentConfig =
            gson.fromJson(json, HouseConfig::class.java)

        // 4. Create the safe configuration required by Lab 2.
        val safeConfig = currentConfig.copy(
            target_temperature = 17.0,
            last_updated_by = "Android-Operator"
        )

        // 5. Convert the safe configuration back into JSON.
        val updatedJson = gson.toJson(safeConfig)

        // 6. GitHub requires the new file content in Base64.
        val encodedContent = Base64.encodeToString(
            updatedJson.toByteArray(Charsets.UTF_8),
            Base64.NO_WRAP
        )

        // 7. PUT the corrected configuration directly to main.
        api.updateHouseConfigFile(
            authorization = token,
            owner = owner,
            repo = repo,
            request = UpdateFileRequest(
                message = "Force Merge: restore safe house configuration",
                content = encodedContent,
                sha = fileResponse.sha,
                branch = "main"
            )
        )

        // 8. Only after the configuration update succeeds, close the PR.
        api.updatePullRequest(
            authorization = token,
            owner = owner,
            repo = repo,
            pullNumber = prNumber,
            request = UpdatePullRequestRequest(
                state = "closed"
            )
        )
    }
}