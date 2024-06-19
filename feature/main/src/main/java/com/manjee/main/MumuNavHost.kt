package com.manjee.main

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.manjee.feature.myartist.navigation.MY_ARTIST_ROUTE
import com.manjee.feature.myartist.navigation.myArtistScreen
import com.manjee.info.navigation.INFO_ROUTE
import com.manjee.info.navigation.infoScreen
import com.manjee.lyric.navigation.LYRIC_ROUTE
import com.manjee.lyric.navigation.lyricScreen
import com.manjee.main.navigation.MAIN_ROUTE
import com.manjee.main.navigation.mainScreen
import com.manjee.title.navigation.TITLE_ROUTE
import com.manjee.title.navigation.navigateToTitle
import com.manjee.title.navigation.titleScreen

@Composable
fun MumuNavHost(
    navController: NavHostController = rememberNavController()
) {

    NavHost(
        navController = navController,
        startDestination = MAIN_ROUTE
    ) {
        mainScreen(
            navigateToLyric = { navController.navigate(LYRIC_ROUTE) },
            navigateToTitle = { navController.navigateToTitle(it) },
            navigateToArtist = { navController.navigate(MY_ARTIST_ROUTE) },
            navigateToInfo = { navController.navigate(INFO_ROUTE) }
        )
        lyricScreen(
            onBackPressed = { navController.popBackStack() }
        )
        titleScreen(
            onBackPressed = { navController.popBackStack() }
        )
        myArtistScreen(
            onBackPressed = { navController.popBackStack() }
        )
        infoScreen()
    }
}