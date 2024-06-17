package com.manjee.title.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.manjee.title.TitleRoute

const val TITLE_ROUTE = "title"
const val QUIZ_ID = "quizId"

object TitleNavigation {
    fun detailRoute(quizId: String) = "$TITLE_ROUTE/$quizId"
}

fun NavController.navigateToTitle(
    quizId: String,
    navOptions: NavOptions = NavOptions.Builder().build(),
) {
    navigate(TitleNavigation.detailRoute(quizId), navOptions)
}

fun NavGraphBuilder.titleScreen(
    onBackPressed: () -> Unit,
) {
    composable(
        route = TitleNavigation.detailRoute("{$QUIZ_ID}"),
        arguments = listOf(
            navArgument(QUIZ_ID) {
                type = NavType.StringType
            }
        )
    ) {
        val quizId = it.arguments?.getString(QUIZ_ID) ?: ""

        TitleRoute(
            quizId = quizId,
            onBackPressed = onBackPressed
        )
    }
}