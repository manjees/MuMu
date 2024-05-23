package com.manjee.feature.myartist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.manjee.firebase.database.RankingDatabase
import com.manjee.model.Artist
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyArtistViewModel @Inject constructor(
    private val rankingDataBase: RankingDatabase
) : ViewModel() {

    private val _uiState: MutableStateFlow<MyArtistScreenUiState> =
        MutableStateFlow(MyArtistScreenUiState.Loading)
    val uiState: StateFlow<MyArtistScreenUiState> = _uiState

    private val artistList = mutableListOf<Artist>()

    init {
        getArtistList()
    }

    private fun getArtistList() = viewModelScope.launch {
        rankingDataBase.getArtistData {
            artistList.addAll(it)

            _uiState.value = MyArtistScreenUiState.Success(it)
        }
    }

    fun search(keyword: String) {
        val filteredList = artistList.filter { it.name.contains(keyword, ignoreCase = true) }

        _uiState.value = MyArtistScreenUiState.Success(filteredList)
    }
}