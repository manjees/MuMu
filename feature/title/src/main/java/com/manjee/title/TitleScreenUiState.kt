package com.manjee.title

import androidx.compose.runtime.Stable
import com.manjee.core.datastore.model.MyArtistData
import com.manjee.model.Artist
import com.manjee.model.TitleQuiz

@Stable
interface TitleScreenUiState {
    data object Loading: TitleScreenUiState
    data class Error(val message: String): TitleScreenUiState
    data class Success(
        var data: TitleQuiz,
        var correctCount: Long = 0,
        var incorrectCount: Long = 0,
        var currentQuizIndex: Int = 0,
    ): TitleScreenUiState
    data class End(
        val myArtist: Artist?
    ): TitleScreenUiState
}