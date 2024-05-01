@file:OptIn(ExperimentalFoundationApi::class)

package com.manjee.splash

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.lerp
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.util.lerp
import com.manjee.designsystem.ui.Yellow40
import kotlin.math.absoluteValue

@Composable
fun SplashRoute() {
    SplashScreen()
}

@Composable
internal fun SplashScreen() {
    val configuration = LocalConfiguration.current
    val pagerState = rememberPagerState(0, pageCount = { 10 })

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Text(
            modifier = Modifier.padding(24.dp),
            text = "Quiz",
            fontWeight = FontWeight.Bold,
            fontSize = 32.sp
        )
        HorizontalPager(
            state = pagerState,
            contentPadding = PaddingValues(horizontal = configuration.screenWidthDp.dp / 8),
        ) {
            Card(
                Modifier
                    .size(300.dp)
                    .graphicsLayer {
                        val pageOffset = (
                                (pagerState.currentPage - it) + pagerState
                                    .currentPageOffsetFraction
                                ).absoluteValue

                        alpha = lerp(
                            start = 0.5f,
                            stop = 1f,
                            fraction = 1f - pageOffset.coerceIn(0f, 1f)
                        )
                    }
            ) {
                Text(text = "Page $it", fontSize = 24.sp, color = Yellow40)
            }
        }
    }
}

@Composable
@Preview
fun SplashScreenPreview() {
    SplashScreen()
}