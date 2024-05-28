package com.manjee.feature.myartist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.manjee.core.datastore.datasource.CachingPreferenceDataSource
import com.manjee.core.datastore.model.MyArtistData
import com.manjee.firebase.database.RankingDatabase
import com.manjee.model.Artist
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyArtistViewModel @Inject constructor(
    private val rankingDataBase: RankingDatabase,
    private val cachingDataSource: CachingPreferenceDataSource
) : ViewModel() {

    private val _uiState: MutableStateFlow<MyArtistScreenUiState> =
        MutableStateFlow(MyArtistScreenUiState.Loading)
    val uiState: StateFlow<MyArtistScreenUiState> = _uiState

    private val artistList = mutableListOf<Artist>()

    init {
        getArtistList()
    }

    private fun getArtistList() = viewModelScope.launch {
        try {
            val artistList = rankingDataBase.getArtistData()
            _uiState.value = MyArtistScreenUiState.Success(artistList)
        } catch (e: Exception) {
            _uiState.value = MyArtistScreenUiState.Error(e.message ?: "An error occurred")
        }
    }

    fun search(keyword: String) {
        val filteredList = artistList.filter { it.name.contains(keyword, ignoreCase = true) }

        _uiState.value = MyArtistScreenUiState.Success(filteredList)
    }

    fun updateMyArtist(artist: Artist) {
        viewModelScope.launch {
            cachingDataSource.updateMyArtistData(MyArtistData(id = artist.id, name = artist.name))
            _uiState.value = MyArtistScreenUiState.Complete
        }
    }
}