package com.manjee.lyric.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.manjee.lyric.LyricRoute

const val LYRIC_ROUTE = "lyric"

fun NavController.navigateToLyric(navOptions: NavOptions = NavOptions.Builder().build()) {
    navigate(LYRIC_ROUTE)
}

fun NavGraphBuilder.lyricScreen() {
    composable(
        route = LYRIC_ROUTE
    ) {
        LyricRoute()
    }
}