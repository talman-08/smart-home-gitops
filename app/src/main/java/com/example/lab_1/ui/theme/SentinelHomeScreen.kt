package com.example.lab_1.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun SentinelHomeScreen(
    uiState: SentinelUiState
) {

    when (uiState) {

        SentinelUiState.Loading -> {
            LoadingScreen()
        }

        SentinelUiState.Normal -> {
            NormalScreen()
        }

        is SentinelUiState.SecurityAlert -> {
            SecurityAlertScreen(
                message = uiState.message,
                confidence = uiState.confidence
            )
        }

        is SentinelUiState.Error -> {
            ErrorScreen(
                message = uiState.message
            )
        }
    }
}

@Composable
private fun LoadingScreen() {

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}

@Composable
private fun NormalScreen() {

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF2E7D32)),
        contentAlignment = Alignment.Center
    ) {

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            Text(
                text = "SYSTEM NORMAL",
                color = Color.White,
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold
            )

            Text(
                text = "No deceptive activity detected.",
                color = Color.White,
                modifier = Modifier.padding(top = 12.dp)
            )
        }
    }
}

@Composable
private fun SecurityAlertScreen(
    message: String,
    confidence: Int
) {

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFB71C1C))
            .padding(24.dp)
    ) {

        Column(
            modifier = Modifier.align(Alignment.Center)
        ) {

            Text(
                text = "SECURITY ALERT",
                color = Color.White,
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold
            )

            Text(
                text = "Confidence: $confidence%",
                color = Color.White,
                fontSize = 20.sp,
                modifier = Modifier.padding(top = 20.dp)
            )

            Text(
                text = "Detected AI message:",
                color = Color.White,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(top = 24.dp)
            )

            Text(
                text = message,
                color = Color.White,
                modifier = Modifier.padding(top = 8.dp)
            )
        }
    }
}

@Composable
private fun ErrorScreen(
    message: String
) {

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {

        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                text = "Connection Error",
                color = MaterialTheme.colorScheme.error,
                fontWeight = FontWeight.Bold
            )

            Text(
                text = message,
                modifier = Modifier.padding(top = 8.dp)
            )
        }
    }
}