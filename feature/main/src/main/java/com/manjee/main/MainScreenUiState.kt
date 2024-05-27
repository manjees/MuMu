package com.manjee.main

import androidx.compose.runtime.Stable
import com.manjee.model.Artist

@Stable
interface MainScreenUiState {
    data object Loading : MainScreenUiState
    data class Error(val message: String) : MainScreenUiState
    data class Success(
        val isShowArtistDialog: Boolean,
        val myArtist: Artist?,
        val rankingList: List<Artist>
    ) : MainScreenUiState
}