package com.manjee.title

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.manjee.core.datastore.datasource.CachingPreferenceDataSource
import com.manjee.firebase.database.BillboardDatabase
import com.manjee.firebase.database.RankingDatabase
import com.manjee.firebase.database.TitleDatabase
import com.manjee.model.Artist
import com.manjee.model.TitleTheme
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TitleViewModel @Inject constructor(
    private val titleDatabase: TitleDatabase,
    private val billboardDatabase: BillboardDatabase,
    private val rankingDatabase: RankingDatabase,
    private val cachingPreferenceDataSource: CachingPreferenceDataSource
) : ViewModel() {

    private val _uiState: MutableStateFlow<TitleScreenUiState> =
        MutableStateFlow(TitleScreenUiState.Loading)
    val uiState: StateFlow<TitleScreenUiState> = _uiState

    private var myArtistData: Artist? = null

    init {
        getMyArtist()
    }

    fun getQuizData(quizId: String) {
        if (quizId == TitleTheme.K_POP.id) {
            getTitleData()
        } else {
            getBillboardData()
        }
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

    private fun getBillboardData() = viewModelScope.launch {
        billboardDatabase {
            it.data?.let { data ->
                _uiState.value = TitleScreenUiState.Success(data)
            } ?: run {
                _uiState.value = TitleScreenUiState.Error(it.error ?: "Error")
            }
        }
    }

    private fun getMyArtist() = viewModelScope.launch {
        myArtistData = cachingPreferenceDataSource.myArtistData.firstOrNull()
    }

    fun correctAnswer() {
        val currentState = _uiState.value as? TitleScreenUiState.Success ?: return

        val isEnd = currentState.currentQuizIndex + 1 == currentState.data.choiceList.size

        if (isEnd) {
            updateRankingData()
            return
        }

        _uiState.value = currentState.copy(
            correctCount = currentState.correctCount + 1,
            currentQuizIndex = currentState.currentQuizIndex + 1,
        )
    }

    fun incorrectAnswer() {
        val currentState = _uiState.value as? TitleScreenUiState.Success ?: return

        val isEnd = currentState.currentQuizIndex + 1 == currentState.data.choiceList.size

        if (isEnd) {
            updateRankingData()
            return
        }

        _uiState.value = currentState.copy(
            incorrectCount = currentState.incorrectCount + 1,
            currentQuizIndex = currentState.currentQuizIndex + 1,
        )
    }

    private fun updateRankingData() = viewModelScope.launch {
        myArtistData?.let {
            rankingDatabase.updateData(
                it.id,
                (uiState.value as TitleScreenUiState.Success).correctCount
            ) {
                _uiState.value = TitleScreenUiState.End(myArtistData)
            }
        }
    }
}