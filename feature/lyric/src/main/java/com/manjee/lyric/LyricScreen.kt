package com.manjee.lyric

import android.util.Log
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.manjee.model.Lyric
import kotlinx.coroutines.launch

@Composable
fun LyricRoute() {
    LyricScreen()
}

private var quizList by mutableStateOf(
    listOf(
        Lyric(1, "1"),
        Lyric(2, "2"),
        Lyric(3, "3"),
        Lyric(4, "4"),
        Lyric(5, "5")
    )
)
private var selectList by mutableStateOf(listOf<Lyric>())

@Composable
internal fun LyricScreen() {
    val coroutineScope = rememberCoroutineScope()

    var isAnswerListClick by remember { mutableStateOf(false) }
    var isChoiceListClick by remember { mutableStateOf(false) }

    LaunchedEffect(quizList) {
        quizList = quizList.toMutableList()
        Log.i("@@@@", "$quizList")
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        LazyColumn {
            itemsIndexed(
                key = { index, item -> item.index },
                items = selectList
            ) { _, item ->
                val scale = remember { Animatable(1f) } // 초기화

                Text(
                    modifier = Modifier
                        .scale(scale.value)
                        .clickable {
                            if (isAnswerListClick) {
                                return@clickable
                            }
                            isAnswerListClick = true

                            val job = coroutineScope.launch {
                                scale.animateTo(
                                    targetValue = if (scale.isRunning) 1f else 0f,
                                    animationSpec = spring(
                                        dampingRatio = Spring.DampingRatioMediumBouncy,
                                        stiffness = Spring.StiffnessMedium
                                    )
                                )
                            }

                            job.invokeOnCompletion {
                                quizList = quizList
                                    .toMutableList()
                                    .map {
                                        if (it.index == item.index) {
                                            it.copy(isVisible = true)
                                        } else {
                                            it
                                        }
                                    }

                                selectList = selectList
                                    .toMutableList()
                                    .apply { remove(item) }

                                isAnswerListClick = false
//                                }
                            }
                        },
                    text = item.str
                )

                LaunchedEffect(item) {
                    scale.animateTo(
                        targetValue = 1f,
                        animationSpec = spring(
                            dampingRatio = Spring.DampingRatioMediumBouncy,
                            stiffness = Spring.StiffnessLow
                        )
                    )
                }
            }
        }
        LazyColumn {
            itemsIndexed(quizList) { index, item ->
                val scale = remember { Animatable(1f) }

                if (item.isVisible) {
                    Text(
                        modifier = Modifier
                            .scale(scale.value)
                            .clickable {
                                if (isChoiceListClick) {
                                    return@clickable
                                }
                                isChoiceListClick = true

                                val job = coroutineScope.launch {
                                    scale.animateTo(
                                        targetValue = 0f,
                                        animationSpec = spring(
                                            dampingRatio = Spring.DampingRatioMediumBouncy,
                                            stiffness = Spring.StiffnessMedium
                                        )
                                    )
                                }

                                job.invokeOnCompletion {
                                    selectList = selectList
                                        .toMutableList()
                                        .apply { add(item) }

                                    quizList = quizList
                                        .toMutableList()
                                        .apply { set(index, item.copy(isVisible = false)) }

                                    isChoiceListClick = false
                                }
                            },
                        text = item.str,
                        fontSize = 20.sp
                    )

                    LaunchedEffect(item) {
                        scale.animateTo(
                            targetValue = 1f,
                            animationSpec = spring(
                                dampingRatio = Spring.DampingRatioMediumBouncy,
                                stiffness = Spring.StiffnessLow
                            )
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Composable
internal fun LyricScreenPreview() {
    LyricScreen()
}