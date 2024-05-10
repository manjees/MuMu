package com.manjee.lyric

import androidx.compose.runtime.Stable
import com.manjee.model.Lyric
import com.manjee.model.LyricQuiz

@Stable
interface LyricScreenUiState {
    data object Loading : LyricScreenUiState
    data class Error(val message: String) : LyricScreenUiState
    data class Success(
        var data: LyricQuiz,
        var quizCount: Int = 0,
        var correctCount: Int = 0,
        var currentQuizIndex: Int = 0,
    ) : LyricScreenUiState
}