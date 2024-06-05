package com.manjee.main

import androidx.compose.runtime.Stable
import com.manjee.model.Artist
import com.manjee.model.MainScreenData

@Stable
interface MainScreenUiState {
    data object Loading : MainScreenUiState
    data class Error(val message: String) : MainScreenUiState
    data class Success(
        val mainScreenData: MainScreenData
    ) : MainScreenUiState
}