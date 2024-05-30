package com.manjee.designsystem.component

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition

@Composable
fun LottieLoader(
    modifier: Modifier = Modifier,
    lottieState: LottieState = LottieState.Idle,
    interations: Int = 1,
    isPause: Boolean = false,
    isEnd: () -> Unit
) {

    val isPlaying = lottieState is LottieState.Playing

    val lottieSpec = LottieCompositionSpec.RawRes(
        resId = when (lottieState) {
            is LottieState.Playing -> lottieState.lottieResId
            else -> 0
        }
    )

    val lottieComposition by rememberLottieComposition(spec = lottieSpec)

    val lottieProgress by animateLottieCompositionAsState(
        lottieComposition,
        iterations = interations,
        isPlaying = if (!isPause) isPlaying else false,
    )

    LaunchedEffect(lottieProgress) {
        if (lottieProgress == 1f) {
            isEnd()
        }
    }

    LottieAnimation(
        modifier = modifier,
        composition = lottieComposition,
        progress = { lottieProgress },
        contentScale = ContentScale.Crop
    )
}