package com.manjee.info.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.manjee.info.InfoRoute

const val INFO_ROUTE = "info"

fun NavGraphBuilder.infoScreen() {
    composable(
        route = INFO_ROUTE
    ) {
        InfoRoute()
    }
}