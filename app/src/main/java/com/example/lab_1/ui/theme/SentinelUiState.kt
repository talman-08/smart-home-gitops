package com.example.lab_1.ui

sealed class SentinelUiState {

    data object Loading : SentinelUiState()

    data object Normal : SentinelUiState()

    data class SecurityAlert(
        val message: String,
        val confidence: Int
    ) : SentinelUiState()

    data class Error(
        val message: String
    ) : SentinelUiState()
}