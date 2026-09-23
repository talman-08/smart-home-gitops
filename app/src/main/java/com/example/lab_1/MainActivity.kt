package com.example.lab_1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.lab_1.ui.SentinelHomeScreen
import com.example.lab_1.ui.theme.Lab_1Theme
import com.example.lab_1.viewmodel.SentinelHomeViewModel

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {

            Lab_1Theme {

                val sentinelViewModel: SentinelHomeViewModel = viewModel()

                val uiState by sentinelViewModel.uiState.collectAsState()

                SentinelHomeScreen(
                    uiState = uiState
                )
            }
        }
    }
}