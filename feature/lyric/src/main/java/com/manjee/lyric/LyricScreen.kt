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
import androidx.compose.runtime.mutableIntStateOf
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

private var selectedIndex by mutableIntStateOf(-1)
private var testList by mutableStateOf(listOf(Lyric("1"), Lyric("2"), Lyric("3"), Lyric("4"), Lyric("5")))
private var aList by mutableStateOf(listOf<Lyric>())

@Composable
internal fun LyricScreen() {
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(testList) {
        testList = testList.toMutableList()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        LazyColumn {
            itemsIndexed(aList) { index, item ->
                val scale = remember { Animatable(1f) } // 초기화

                Text(
                    modifier = Modifier
                        .scale(scale.value)
                        .clickable {

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
            itemsIndexed(testList) { index, item ->
                val scale = remember { Animatable(1f) }

                if (item.isVisible) {
                    Text(
                        modifier = Modifier
                            .scale(scale.value)
                            .clickable {
                                selectedIndex = index

                                val job = coroutineScope.launch {
                                    scale.animateTo(
                                        targetValue = if (scale.isRunning) 1f else 0f,
                                        animationSpec = spring(
                                            dampingRatio = Spring.DampingRatioMediumBouncy,
                                            stiffness = Spring.StiffnessLow
                                        )
                                    )
                                }

                                job.invokeOnCompletion {
                                    if (index == selectedIndex) {
                                        aList = aList
                                            .toMutableList()
                                            .apply { add(item) }

                                        testList = testList
                                            .toMutableList()
                                            .apply { set(index, item.copy(isVisible = false)) }

                                        selectedIndex = -1
                                    }
                                }
                            },
                        text = item.str,
                        fontSize = 20.sp
                    )
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