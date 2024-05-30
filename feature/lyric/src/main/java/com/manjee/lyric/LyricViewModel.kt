package com.manjee.lyric

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.manjee.core.datastore.datasource.CachingPreferenceDataSource
import com.manjee.firebase.database.LyricDatabase
import com.manjee.firebase.database.RankingDatabase
import com.manjee.model.Artist
import com.manjee.model.Lyric
import com.manjee.model.LyricQuiz
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LyricViewModel @Inject constructor(
    private val lyricDatabase: LyricDatabase,
    private val rankingDatabase: RankingDatabase,
    private val cachingPreferenceDataSource: CachingPreferenceDataSource
) : ViewModel() {

    private val _uiState: MutableStateFlow<LyricScreenUiState> =
        MutableStateFlow(LyricScreenUiState.Loading)
    val uiState: StateFlow<LyricScreenUiState> = _uiState

    private var myArtistData: Artist? = null

    init {
        getLyricData()
        getMyArtist()
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

    private fun getMyArtist() = viewModelScope.launch {
        myArtistData = cachingPreferenceDataSource.myArtistData.firstOrNull()
    }

    fun checkAnswer(answerList: List<Lyric>, answer: String) {
        val currentState = _uiState.value as? LyricScreenUiState.Success ?: return

        val myAnswer = answerList.map { it.str }.joinToString(separator = "") { it }
        val isCorrect = myAnswer == answer

        if (currentState.currentQuizIndex == currentState.quizCount - 1) {
            updateRankingData()
            return
        }

        _uiState.value = currentState.copy(
            correctCount = currentState.correctCount + if (isCorrect) 1 else 0,
            currentQuizIndex = currentState.currentQuizIndex + 1
        )
    }

    private fun updateRankingData() = viewModelScope.launch {
        val score = (uiState.value as LyricScreenUiState.Success).correctCount * 10

        myArtistData?.let {
            rankingDatabase.updateData(
                it.id,
                score
            ) {
                _uiState.value = LyricScreenUiState.End(myArtistData, score)
            }
        } ?: run {
            _uiState.value = LyricScreenUiState.End(null, score)
        }
    }

    companion object {
        const val TAG = "LyricViewModel"
    }
}