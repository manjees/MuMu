package com.manjee.title.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.manjee.title.TitleRoute

const val TITLE_ROUTE = "title"

fun NavController.navigateToTitle(navOptions: NavOptions = NavOptions.Builder().build()) {
    navigate(TITLE_ROUTE)
}

fun NavGraphBuilder.titleScreen() {
    composable(
        route = TITLE_ROUTE
    ) {
        TitleRoute()
    }
}