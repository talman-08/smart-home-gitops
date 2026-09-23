package com.example.lab_1.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lab_1.data.api.SentinelRetrofitClient
import com.example.lab_1.data.repository.SentinelGitHubRepository
import com.example.lab_1.domain.SentinelDeceptionDetector
import com.example.lab_1.ui.SentinelUiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SentinelHomeViewModel : ViewModel() {

    private val repository =
        SentinelGitHubRepository(SentinelRetrofitClient.api)

    private val detector =
        SentinelDeceptionDetector()

    private val _uiState =
        MutableStateFlow<SentinelUiState>(SentinelUiState.Loading)

    val uiState: StateFlow<SentinelUiState> =
        _uiState.asStateFlow()

    private val owner = "talman-08"
    private val repo = "smart-home-gitops"

    init {
        startPolling()
    }

    private fun startPolling() {

        viewModelScope.launch(Dispatchers.IO) {

            while (true) {

                try {
                    checkGitHub()
                } catch (e: Exception) {
                    _uiState.value =
                        SentinelUiState.Error(
                            e.message ?: "Unknown error"
                        )
                }

                delay(30_000)
            }
        }
    }

    private suspend fun checkGitHub() {

        val comments =
            repository.getCommentsFromOpenPullRequests(
                owner = owner,
                repo = repo
            )

        var attack: SentinelUiState.SecurityAlert? = null

        for (comment in comments) {

            val text = comment.body ?: continue

            val result = withContext(Dispatchers.Default) {
                detector.analyze(text)
            }

            if (result.isAttack) {

                attack = SentinelUiState.SecurityAlert(
                    message = text,
                    confidence = result.confidence
                )

                break
            }
        }

        _uiState.value =
            attack ?: SentinelUiState.Normal
    }
}