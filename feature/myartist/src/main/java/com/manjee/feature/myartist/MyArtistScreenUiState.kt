package com.manjee.feature.myartist

import androidx.compose.runtime.Stable
import com.manjee.model.Artist

@Stable
interface MyArtistScreenUiState {
    data object Loading: MyArtistScreenUiState
    data class Error(val message: String): MyArtistScreenUiState
    data class Success(
        var data: List<Artist>,
    ): MyArtistScreenUiState
    data object Complete: MyArtistScreenUiState
}