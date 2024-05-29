package com.manjee.title

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.manjee.core.datastore.datasource.CachingPreferenceDataSource
import com.manjee.core.datastore.model.MyArtistData
import com.manjee.firebase.database.RankingDatabase
import com.manjee.firebase.database.TitleDatabase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TitleViewModel @Inject constructor(
    private val titleDatabase: TitleDatabase,
    private val rankingDatabase: RankingDatabase,
    private val cachingPreferenceDataSource: CachingPreferenceDataSource
) : ViewModel() {

    private val _uiState: MutableStateFlow<TitleScreenUiState> =
        MutableStateFlow(TitleScreenUiState.Loading)
    val uiState: StateFlow<TitleScreenUiState> = _uiState

    private var myArtistData: MyArtistData? = null

    init {
        getTitleData()
        getMyArtist()
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

    private fun getMyArtist() = viewModelScope.launch {
        cachingPreferenceDataSource.myArtistData.firstOrNull()
    }

    fun correctAnswer() {
        val currentState = _uiState.value as? TitleScreenUiState.Success ?: return

        val isEnd = currentState.currentQuizIndex + 1 == currentState.data.choiceList.size

        if (isEnd) {
            updateRankingData()
        }

        _uiState.value = currentState.copy(
            correctCount = currentState.correctCount + 1,
            currentQuizIndex = currentState.currentQuizIndex + 1,
            isEnd = isEnd
        )
    }

    fun incorrectAnswer() {
        val currentState = _uiState.value as? TitleScreenUiState.Success ?: return

        val isEnd = currentState.currentQuizIndex + 1 == currentState.data.choiceList.size

        if (isEnd) {
            updateRankingData()
        }

        _uiState.value = currentState.copy(
            incorrectCount = currentState.incorrectCount + 1,
            currentQuizIndex = currentState.currentQuizIndex + 1,
            isEnd = isEnd
        )
    }

    private fun updateRankingData() = viewModelScope.launch {
        myArtistData?.let {
            rankingDatabase.updateData(it.id, (uiState.value as TitleScreenUiState.Success).correctCount)
        }
    }
}