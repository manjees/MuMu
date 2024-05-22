package com.manjee.lyric

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.manjee.firebase.database.LyricDatabase
import com.manjee.model.Lyric
import com.manjee.model.LyricQuiz
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LyricViewModel @Inject constructor(
    private val lyricDatabase: LyricDatabase
) : ViewModel() {

    private val _uiState: MutableStateFlow<LyricScreenUiState> =
        MutableStateFlow(LyricScreenUiState.Loading)
    val uiState: StateFlow<LyricScreenUiState> = _uiState

    init {
        getLyricData()
    }

    private fun getLyricData() = viewModelScope.launch {
        lyricDatabase {
            it.data?.let { data ->
                _uiState.value = LyricScreenUiState.Success(data, quizCount = data.size)
            } ?: run {
                _uiState.value = LyricScreenUiState.Error(it.error ?: "Error")
            }
        }
    }

    fun checkAnswer(answerList: List<Lyric>, answer: String) {
        val currentState = _uiState.value as? LyricScreenUiState.Success ?: return

        val myAnswer = answerList.map { it.str }.joinToString(separator = "") { it }
        val isCorrect = myAnswer == answer

        if (currentState.currentQuizIndex == currentState.quizCount - 1) {
            Log.d(TAG, "Quiz finished")
            return
        }

        _uiState.value = currentState.copy(
            correctCount = currentState.correctCount + if (isCorrect) 1 else 0,
            currentQuizIndex = currentState.currentQuizIndex + 1
        )
    }

    companion object {
        const val TAG = "LyricViewModel"
    }
}