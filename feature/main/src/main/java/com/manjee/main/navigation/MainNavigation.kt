package com.manjee.main.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.manjee.main.MainRoute

const val MAIN_ROUTE = "main"

fun NavController.navigateToMain(navOptions: NavOptions = NavOptions.Builder().build()) {
    navigate(MAIN_ROUTE)
}

fun NavGraphBuilder.mainScreen(
    navigateToLyric: () -> Unit,
    navigateToTitle: (quizId: String) -> Unit,
    navigateToArtist: () -> Unit,
    navigateToInfo: () -> Unit
) {
    composable(
        route = MAIN_ROUTE
    ) {
        MainRoute(
            navigateToLyric = navigateToLyric,
            navigateToTitle = navigateToTitle,
            navigateToArtist = navigateToArtist,
            navigateToInfo = navigateToInfo
        )
    }
}