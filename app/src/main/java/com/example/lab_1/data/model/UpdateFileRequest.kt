package com.example.lab_1.data.model

data class UpdateFileRequest(
    val message: String,
    val content: String,
    val sha: String,
    val branch: String
)