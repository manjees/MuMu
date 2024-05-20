package com.manjee.title

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.manjee.firebase.database.TitleDatabase
import com.manjee.model.Title
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TitleViewModel @Inject constructor(
    private val titleDatabase: TitleDatabase
) : ViewModel() {

    private val _uiState: MutableStateFlow<TitleScreenUiState> = MutableStateFlow(TitleScreenUiState.Loading)
    val uiState: StateFlow<TitleScreenUiState> = _uiState

    init {
        getTitleData()
    }

    private fun getTitleData() = viewModelScope.launch {
        titleDatabase {
            it.data?.let { data ->
                _uiState.value = TitleScreenUiState.Success(data)
            } ?: run {
                _uiState.value = TitleScreenUiState.Error(it.error ?: "Error")
            }
        }
    }

    fun correctAnswer() {
        val currentState = _uiState.value as? TitleScreenUiState.Success ?: return

        val isEnd = currentState.currentQuizIndex + 1 == currentState.data.choiceList.size

        _uiState.value = currentState.copy(correctCount = currentState.correctCount + 1, currentQuizIndex = currentState.currentQuizIndex + 1, isEnd = isEnd)
    }

    fun incorrectAnswer() {
        val currentState = _uiState.value as? TitleScreenUiState.Success ?: return

        val isEnd = currentState.currentQuizIndex + 1 == currentState.data.choiceList.size

        _uiState.value = currentState.copy(incorrectCount = currentState.incorrectCount + 1, currentQuizIndex = currentState.currentQuizIndex + 1, isEnd = isEnd)
    }
}