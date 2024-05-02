@file:OptIn(ExperimentalFoundationApi::class)

package com.manjee.main

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.util.lerp
import com.manjee.designsystem.ui.Blue20
import com.manjee.designsystem.ui.Green20
import com.manjee.designsystem.ui.Grey40
import com.manjee.designsystem.ui.Grey60
import com.manjee.designsystem.ui.Grey90
import com.manjee.designsystem.ui.Pink20
import com.manjee.designsystem.ui.Red20
import com.manjee.designsystem.ui.Yellow10
import com.manjee.feature.splash.R
import com.manjee.main.component.RankItem
import component.NoPaddingText
import kotlin.math.absoluteValue

@Composable
fun MainRoute() {
    MainScreen()
}

@Composable
internal fun MainScreen() {
    val configuration = LocalConfiguration.current
    val pagerState = rememberPagerState(0, pageCount = { QuizTheme.entries.size })

    val cardColorList = listOf(Red20, Green20, Blue20, Pink20)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Yellow10)
    ) {
        NoPaddingText(
            modifier = Modifier.padding(horizontal = 24.dp, vertical = 8.dp),
            text = stringResource(id = R.string.title),
            fontWeight = FontWeight.Bold,
            fontSize = 52.sp,
            color = Grey90
        )
        NoPaddingText(
            modifier = Modifier
                .padding(horizontal = 24.dp, vertical = 8.dp),
            text = stringResource(id = R.string.subtitle),
            fontSize = 14.sp,
            color = Grey40
        )
        HorizontalPager(
            state = pagerState,
            contentPadding = PaddingValues(
                horizontal = configuration.screenWidthDp.dp / 8,
                vertical = 0.dp
            ),
        ) {
            val theme: QuizTheme = QuizTheme.entries[it]

            Card(
                Modifier
                    .size(300.dp)
                    .padding(16.dp)
                    .shadow(10.dp)
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
                Box(
                    modifier = Modifier
                        .background(cardColorList[it])
                        .fillMaxSize()
                        .padding(16.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize(),
                        verticalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        NoPaddingText(
                            text = theme.title,
                            color = Grey60,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold
                        )
                        NoPaddingText(
                            text = theme.subTitle,
                            color = Grey40,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }
            }
        }
        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .background(Color.Transparent)
        ) {
            items(100) {
                RankItem()
            }
        }
    }
}

@Composable
@Preview
fun MainScreenPreview() {
    MainScreen()
}