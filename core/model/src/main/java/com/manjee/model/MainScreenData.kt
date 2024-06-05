package com.manjee.model

data class MainScreenData(
    val isRequestVisible: Boolean,
    val myArtist: Artist?,
    val rankingList: List<Artist>,
    val themeList: List<Theme>
)
