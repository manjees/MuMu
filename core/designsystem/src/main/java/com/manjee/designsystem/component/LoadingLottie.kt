package com.manjee.designsystem.component

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.manjee.designsystem.R

@Composable
fun LoadingLottie(
    modifier: Modifier = Modifier,
) {
    LottieLoader(
        modifier = modifier,
        lottieState = LottieState.Playing(R.raw.loading),
        isEnd = {}
    )
}