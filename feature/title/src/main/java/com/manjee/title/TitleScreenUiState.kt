package com.manjee.title

import androidx.compose.runtime.Stable
import com.manjee.model.TitleQuiz

@Stable
interface TitleScreenUiState {
    data object Loading: TitleScreenUiState
    data class Error(val message: String): TitleScreenUiState
    data class Success(
        var data: TitleQuiz,
        var correctCount: Int = 0,
        var incorrectCount: Int = 0,
        var currentQuizIndex: Int = 0,
        var isEnd: Boolean = false
    ): TitleScreenUiState
}