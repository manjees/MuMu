package com.manjee.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.manjee.core.datastore.datasource.CachingPreferenceDataSource
import com.manjee.core.domain.usecase.ShowArtistSelectDialogUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val showArtistSelectDialogUseCase: ShowArtistSelectDialogUseCase,
    private val cachingDataSource: CachingPreferenceDataSource
) : ViewModel() {

    private val _showArtistRequestDialog = MutableStateFlow(false)
    val showArtistRequestDialog: StateFlow<Boolean> = _showArtistRequestDialog

    init {
        checkShowArtistRequestDialog()
    }

    private fun checkShowArtistRequestDialog() = viewModelScope.launch {
        showArtistSelectDialogUseCase().collectLatest {
            _showArtistRequestDialog.value = it
        }
    }

    fun updateShowArtistRequestDialog() {
        viewModelScope.launch {
            cachingDataSource.updateIsRequestVisible(false)
            _showArtistRequestDialog.value = false
        }
    }
}