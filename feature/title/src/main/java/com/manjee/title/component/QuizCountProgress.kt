package com.manjee.title.component

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.tooling.preview.Preview

//strokeCap = StrokeCap.Round,
//                                    color = Red70,
//                                    trackColor = Color.White
@Composable
fun QuizCountProgress(
    modifier: Modifier = Modifier,
    quizCount: Int,
    count: Long,
    color: Color = Color.White,
    trackColor: Color = Color.Black,
    isReverse: Boolean = false,
) {
    val progress = if (isReverse) {
        1 - (count / quizCount.toFloat())
    } else {
        count / quizCount.toFloat()
    }

    LinearProgressIndicator(
        modifier = modifier,
        progress = { progress },
        strokeCap = StrokeCap.Round,
        color = color,
        trackColor = trackColor
    )
}

@Preview
@Composable
fun QuizCountProgressPreview() {
    Column {
        QuizCountProgress(
            quizCount = 10,
            count = 3
        )
        QuizCountProgress(
            quizCount = 10,
            count = 3,
            isReverse = true
        )
    }
}