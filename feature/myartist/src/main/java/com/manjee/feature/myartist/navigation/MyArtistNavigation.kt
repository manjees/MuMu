package com.manjee.feature.myartist.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.manjee.feature.myartist.MyArtistRoute

const val MY_ARTIST_ROUTE = "my_artist"

fun NavController.navigateToMyArtist(navOptions: NavOptions = NavOptions.Builder().build()) {
    navigate(MY_ARTIST_ROUTE, navOptions)
}

fun NavGraphBuilder.myArtistScreen(
    onBackPressed: () -> Unit
) {
    composable(
        route = MY_ARTIST_ROUTE
    ) {
        MyArtistRoute(
            onBackPressed = onBackPressed
        )
    }
}