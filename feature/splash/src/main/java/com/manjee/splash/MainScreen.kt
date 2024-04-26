@file:OptIn(ExperimentalFoundationApi::class)

package com.manjee.splash

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.manjee.designsystem.ui.Yellow40

@Composable
fun MainRoute() {
    MainScreen()
}

@Composable
internal fun MainScreen() {
    val pagerState = rememberPagerState(0, pageCount = { 4 })

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
            state = pagerState
        ) {
            if (it != 3) {
                Box(
                    modifier = Modifier
                        .size(350.dp)
                        .background(Color.Gray)
                ) {

                }
            } else {
                Box(
                    modifier = Modifier
                        .size(350.dp)
                        .background(Yellow40)
                ) {

                }
            }
        }
    }
}

@Composable
@Preview
fun MainScreenPreview() {
    MainScreen()
}