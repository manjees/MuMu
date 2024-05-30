package com.manjee.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.manjee.core.datastore.datasource.CachingPreferenceDataSource
import com.manjee.core.domain.usecase.ShowArtistSelectDialogUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val showArtistSelectDialogUseCase: ShowArtistSelectDialogUseCase,
    private val cachingDataSource: CachingPreferenceDataSource
) : ViewModel() {

    private val _uiState: MutableStateFlow<MainScreenUiState> =
        MutableStateFlow(MainScreenUiState.Loading)
    val uiState: StateFlow<MainScreenUiState> = _uiState

    fun checkShowArtistRequestDialog() = viewModelScope.launch {
        try {
            withContext(Dispatchers.IO) {
                showArtistSelectDialogUseCase().collectLatest { result ->
                    withContext(Dispatchers.Main) {
                        _uiState.value = MainScreenUiState.Success(
                            isShowArtistDialog = result.first,
                            myArtist = result.second,
                            rankingList = result.third
                        )
                    }
                }
            }
        } catch (e: Exception) {
            _uiState.value = MainScreenUiState.Error(e.message ?: "An error occurred")
        }
    }

    fun updateShowArtistRequestDialog() {
        val currentState = _uiState.value

        viewModelScope.launch {
            cachingDataSource.updateIsRequestVisible(false)

            _uiState.value = (currentState as MainScreenUiState.Success).copy(
                isShowArtistDialog = false
            )
        }
    }
}