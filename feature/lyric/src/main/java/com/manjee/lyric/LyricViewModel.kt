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

    private var quizList = mutableListOf<LyricQuiz>()
    private var currentQuizIndex = 0
    private var correctCount = 0

    init {
        getLyricData()
    }

    private fun getLyricData() = viewModelScope.launch {
        lyricDatabase {
            it.data?.let { data ->
                quizList.addAll(data)

                _uiState.value = LyricScreenUiState.Success(data.first(), quizCount = data.size)
            } ?: run {
                _uiState.value = LyricScreenUiState.Error(it.error ?: "Error")
            }
        }
    }

    fun checkAnswer(answerList: List<Lyric>, answer: String) {
        if (currentQuizIndex < quizList.size) {
            val myAnswer = answerList.map { it.str }.joinToString(separator = "") { it }

            if (myAnswer == answer) {
                Log.d(TAG, "Correct")
                correctCount++
            } else {
                Log.d(TAG, "Incorrect")
            }

            currentQuizIndex++
            if (currentQuizIndex < quizList.size) {
                _uiState.value = LyricScreenUiState.Success(
                    quizList[currentQuizIndex],
                    currentQuizIndex = currentQuizIndex,
                    correctCount = correctCount
                )
            } else {
                // All quizzes completed
                _uiState.value = LyricScreenUiState.Success(
                    quizList.last(),
                    currentQuizIndex = currentQuizIndex,
                    correctCount = correctCount
                )
            }
        } else {
            Log.d(TAG, "All quizzes completed")
        }
    }

    companion object {
        const val TAG = "LyricViewModel"
    }
}