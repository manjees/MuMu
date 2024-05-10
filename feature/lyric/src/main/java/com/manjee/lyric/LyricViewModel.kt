package com.manjee.lyric

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.manjee.firebase.database.LyricDatabase
import com.manjee.model.Lyric
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
                _uiState.value = LyricScreenUiState.Success(data)
            } ?: run {
                _uiState.value = LyricScreenUiState.Error(it.error ?: "Error")
            }
        }
    }

    fun checkAnswer(answerList: List<Lyric>, answer: String) {
        Log.i(TAG, "answerList: $answerList, answer: $answer")
    }

    companion object {
        const val TAG = "LyricViewModel"
    }
}